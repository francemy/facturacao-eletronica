package ao.okayulatech.erp.facturacao.model.submission;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
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
@Table(name = "agt_receipts")
public class AgtReceipt {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String validationCode;
	private String receiptNumber;
	private String status;
	private LocalDateTime receivedAt = LocalDateTime.now();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "submission_id")
	private Submission submission;
}
