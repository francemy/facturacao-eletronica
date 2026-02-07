package ao.okayulatech.erp.facturacao.model.submission;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "outbox_events")
public class OutboxEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String submissionUUID;
	private String eventType;

	@Column(columnDefinition = "TEXT")
	private String payload;

	@Enumerated(EnumType.STRING)
	private OutboxStatus status = OutboxStatus.PENDING;

	private int attempts;
	private LocalDateTime nextAttemptAt;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();
}
