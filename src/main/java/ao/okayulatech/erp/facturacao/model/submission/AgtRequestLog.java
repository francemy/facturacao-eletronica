package ao.okayulatech.erp.facturacao.model.submission;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agt_request_logs")
public class AgtRequestLog {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String submissionUUID;
	private int statusCode;
	private String correlationId;

	@Column(columnDefinition = "TEXT")
	private String requestPayload;

	@Column(columnDefinition = "TEXT")
	private String responsePayload;

	private LocalDateTime createdAt = LocalDateTime.now();
}
