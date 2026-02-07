package ao.okayulatech.erp.facturacao.service.submission;

import org.springframework.stereotype.Service;

import ao.okayulatech.erp.facturacao.model.submission.AgtRequestLog;
import ao.okayulatech.erp.facturacao.repository.submission.AgtRequestLogRepository;

@Service
public class AgtAuditService {
	private final AgtRequestLogRepository repository;

	public AgtAuditService(AgtRequestLogRepository repository) {
		this.repository = repository;
	}

	public void logRequest(String submissionUUID, String requestPayload, String responsePayload, int statusCode, String correlationId) {
		AgtRequestLog log = new AgtRequestLog();
		log.setSubmissionUUID(submissionUUID);
		log.setRequestPayload(requestPayload);
		log.setResponsePayload(responsePayload);
		log.setStatusCode(statusCode);
		log.setCorrelationId(correlationId);
		repository.save(log);
	}
}
