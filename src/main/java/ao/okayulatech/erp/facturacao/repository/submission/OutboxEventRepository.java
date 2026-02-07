package ao.okayulatech.erp.facturacao.repository.submission;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ao.okayulatech.erp.facturacao.model.submission.OutboxEvent;
import ao.okayulatech.erp.facturacao.model.submission.OutboxStatus;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
	List<OutboxEvent> findTop20ByStatusInAndNextAttemptAtBefore(List<OutboxStatus> status, LocalDateTime time);
}
