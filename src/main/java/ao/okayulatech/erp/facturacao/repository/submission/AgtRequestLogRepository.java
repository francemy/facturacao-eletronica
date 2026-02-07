package ao.okayulatech.erp.facturacao.repository.submission;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ao.okayulatech.erp.facturacao.model.submission.AgtRequestLog;

public interface AgtRequestLogRepository extends JpaRepository<AgtRequestLog, UUID> {
}
