package ao.okayulatech.erp.facturacao.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
@Table(name = "payment_receipts")
public class PaymentReceipt {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String receiptNo;
	private LocalDate receiptDate;
	private BigDecimal amount;
}
