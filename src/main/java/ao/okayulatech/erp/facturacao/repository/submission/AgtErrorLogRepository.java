package ao.okayulatech.erp.facturacao.repository.submission;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ao.okayulatech.erp.facturacao.model.submission.AgtErrorLog;

public interface AgtErrorLogRepository extends JpaRepository<AgtErrorLog, UUID> {
}
