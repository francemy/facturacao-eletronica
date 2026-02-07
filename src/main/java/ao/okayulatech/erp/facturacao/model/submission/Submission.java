package ao.okayulatech.erp.facturacao.model.submission;

import java.time.LocalDateTime;
import java.util.UUID;

import ao.okayulatech.erp.facturacao.model.InvoiceHeader;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "submissions")
public class Submission {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String submissionUUID;

	@Enumerated(EnumType.STRING)
	private SubmissionStatus status = SubmissionStatus.PENDING;

	private int attempts;
	private LocalDateTime nextAttemptAt;
	private String lastError;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_header_id")
	private InvoiceHeader invoiceHeader;
}
