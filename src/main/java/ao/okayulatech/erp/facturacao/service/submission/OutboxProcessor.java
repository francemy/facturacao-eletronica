package ao.okayulatech.erp.facturacao.service.submission;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ao.okayulatech.erp.facturacao.integration.agt.AgtClient;
import ao.okayulatech.erp.facturacao.integration.agt.AgtSubmissionPayload;
import ao.okayulatech.erp.facturacao.integration.agt.AgtSubmissionResponse;
import ao.okayulatech.erp.facturacao.model.submission.AgtReceipt;
import ao.okayulatech.erp.facturacao.model.submission.OutboxEvent;
import ao.okayulatech.erp.facturacao.model.submission.OutboxStatus;
import ao.okayulatech.erp.facturacao.model.submission.Submission;
import ao.okayulatech.erp.facturacao.model.submission.SubmissionStatus;
import ao.okayulatech.erp.facturacao.repository.submission.AgtReceiptRepository;
import ao.okayulatech.erp.facturacao.repository.submission.OutboxEventRepository;
import ao.okayulatech.erp.facturacao.repository.submission.SubmissionRepository;

@Service
public class OutboxProcessor {
	private static final Logger logger = LoggerFactory.getLogger(OutboxProcessor.class);

	private final OutboxEventRepository outboxEventRepository;
	private final SubmissionRepository submissionRepository;
	private final AgtReceiptRepository receiptRepository;
	private final AgtClient agtClient;
	private final ObjectMapper objectMapper;
	private final AgtAuditService auditService;

	public OutboxProcessor(
		OutboxEventRepository outboxEventRepository,
		SubmissionRepository submissionRepository,
		AgtReceiptRepository receiptRepository,
		AgtClient agtClient,
		ObjectMapper objectMapper,
		AgtAuditService auditService
	) {
		this.outboxEventRepository = outboxEventRepository;
		this.submissionRepository = submissionRepository;
		this.receiptRepository = receiptRepository;
		this.agtClient = agtClient;
		this.objectMapper = objectMapper;
		this.auditService = auditService;
	}

	@Scheduled(fixedDelayString = "${agt.outbox-interval-ms:5000}")
	@Transactional
	public void processOutbox() {
		List<OutboxEvent> events = outboxEventRepository
			.findTop20ByStatusInAndNextAttemptAtBefore(
				List.of(OutboxStatus.PENDING, OutboxStatus.FAILED),
				LocalDateTime.now()
			);
		for (OutboxEvent event : events) {
			processEvent(event);
		}
	}

	private void processEvent(OutboxEvent event) {
		try {
			event.setStatus(OutboxStatus.PROCESSING);
			AgtSubmissionPayload payload = objectMapper.readValue(event.getPayload(), AgtSubmissionPayload.class);
			AgtSubmissionResponse response = agtClient.submit(payload);
			auditService.logRequest(
				payload.getSubmissionUUID(),
				event.getPayload(),
				objectMapper.writeValueAsString(response),
				200,
				MDC.get("X-Correlation-Id")
			);
			event.setStatus(OutboxStatus.SENT);
			event.setUpdatedAt(LocalDateTime.now());
			updateSubmissionStatus(payload.getSubmissionUUID(), response);
		} catch (Exception ex) {
			logger.error("Falha ao processar outbox {}", event.getId(), ex);
			auditService.logRequest(
				event.getSubmissionUUID(),
				event.getPayload(),
				ex.getMessage(),
				500,
				MDC.get("X-Correlation-Id")
			);
			event.setStatus(OutboxStatus.FAILED);
			event.setAttempts(event.getAttempts() + 1);
			event.setNextAttemptAt(LocalDateTime.now().plusMinutes(5));
			event.setUpdatedAt(LocalDateTime.now());
		}
	}

	private void updateSubmissionStatus(String submissionUUID, AgtSubmissionResponse response) {
		Submission submission = submissionRepository.findBySubmissionUUID(submissionUUID)
			.orElseThrow();
		if (response == null) {
			submission.setStatus(SubmissionStatus.FAILED);
			return;
		}
		if ("ACCEPTED".equalsIgnoreCase(response.getStatus())) {
			submission.setStatus(SubmissionStatus.ACCEPTED);
		} else if ("REJECTED".equalsIgnoreCase(response.getStatus())) {
			submission.setStatus(SubmissionStatus.REJECTED);
		} else {
			submission.setStatus(SubmissionStatus.SENT);
		}
		submission.setUpdatedAt(LocalDateTime.now());
		AgtReceipt receipt = new AgtReceipt();
		receipt.setSubmission(submission);
		receipt.setStatus(response.getStatus());
		receipt.setValidationCode(response.getValidationCode());
		receipt.setReceiptNumber(response.getReceiptNumber());
		receiptRepository.save(receipt);
	}
}
