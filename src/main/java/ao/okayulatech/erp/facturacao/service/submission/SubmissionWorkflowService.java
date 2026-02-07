package ao.okayulatech.erp.facturacao.service.submission;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ao.okayulatech.erp.facturacao.integration.agt.AgtMapper;
import ao.okayulatech.erp.facturacao.model.InvoiceHeader;
import ao.okayulatech.erp.facturacao.model.submission.OutboxEvent;
import ao.okayulatech.erp.facturacao.model.submission.OutboxStatus;
import ao.okayulatech.erp.facturacao.model.submission.Submission;
import ao.okayulatech.erp.facturacao.model.submission.SubmissionStatus;
import ao.okayulatech.erp.facturacao.repository.submission.OutboxEventRepository;
import ao.okayulatech.erp.facturacao.repository.submission.SubmissionRepository;

@Service
public class SubmissionWorkflowService {
	private final SubmissionRepository submissionRepository;
	private final OutboxEventRepository outboxEventRepository;
	private final ObjectMapper objectMapper;
	private final AgtMapper agtMapper;

	public SubmissionWorkflowService(
		SubmissionRepository submissionRepository,
		OutboxEventRepository outboxEventRepository,
		ObjectMapper objectMapper,
		AgtMapper agtMapper
	) {
		this.submissionRepository = submissionRepository;
		this.outboxEventRepository = outboxEventRepository;
		this.objectMapper = objectMapper;
		this.agtMapper = agtMapper;
	}

	@Transactional
	public Submission registerSubmission(InvoiceHeader header) {
		if (submissionRepository.existsBySubmissionUUID(header.getSubmissionUUID())) {
			return submissionRepository.findBySubmissionUUID(header.getSubmissionUUID())
				.orElseThrow();
		}
		Submission submission = new Submission();
		submission.setSubmissionUUID(header.getSubmissionUUID());
		submission.setInvoiceHeader(header);
		submission.setStatus(SubmissionStatus.PENDING);
		submission.setNextAttemptAt(LocalDateTime.now());
		Submission saved = submissionRepository.save(submission);
		createOutboxEvent(header);
		return saved;
	}

	private void createOutboxEvent(InvoiceHeader header) {
		try {
			String payload = objectMapper.writeValueAsString(agtMapper.mapSubmission(header));
			OutboxEvent event = new OutboxEvent();
			event.setSubmissionUUID(header.getSubmissionUUID());
			event.setEventType("AGT_SUBMISSION");
			event.setPayload(payload);
			event.setStatus(OutboxStatus.PENDING);
			event.setNextAttemptAt(LocalDateTime.now());
			outboxEventRepository.save(event);
		} catch (Exception ex) {
			throw new IllegalStateException("Falha ao criar evento de outbox.", ex);
		}
	}
}
