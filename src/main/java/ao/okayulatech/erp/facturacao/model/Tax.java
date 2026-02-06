package ao.okayulatech.erp.facturacao.model;

import java.math.BigDecimal;
import java.util.UUID;

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
@Table(name = "taxes")
public class Tax {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String taxType;
	private String taxCountryRegion;
	private String taxCode;
	private BigDecimal percentage;
	private BigDecimal amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_line_id")
	private DocumentLine documentLine;
}
