package ao.okayulatech.erp.facturacao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document_lines")
public class DocumentLine {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private Integer lineNumber;
	private String productCode;
	private String productDescription;
	private BigDecimal quantity;
	private String unitOfMeasure;
	private BigDecimal unitPrice;
	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private BigDecimal settlementAmount;

	@Embedded
	private ReferenceInfo referenceInfo;

	@OneToMany(mappedBy = "documentLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tax> taxes = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_id")
	private Document document;

	public void addTax(Tax tax) {
		tax.setDocumentLine(this);
		this.taxes.add(tax);
	}
}
