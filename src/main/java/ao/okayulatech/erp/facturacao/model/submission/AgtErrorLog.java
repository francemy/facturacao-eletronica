package ao.okayulatech.erp.facturacao.model.submission;

import java.time.LocalDateTime;
import java.util.UUID;

import ao.okayulatech.erp.facturacao.model.Document;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agt_error_logs")
public class AgtErrorLog {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String errorCode;
	private String errorMessage;
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "submission_id")
	private Submission submission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_id")
	private Document document;
}
