package ao.okayulatech.erp.facturacao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "documents")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String documentNo;
	private String documentStatus;
	private String documentType;
	private LocalDate documentDate;
	private LocalDateTime systemEntryDate;
	private String eacCode;
	private String jwsDocumentSignature;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_header_id")
	private InvoiceHeader invoiceHeader;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocumentLine> lines = new ArrayList<>();

	@Embedded
	private DocumentTotals totals;

	@OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WithholdingTax> withholdingTaxes = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "payment_receipt_id")
	private PaymentReceipt paymentReceipt;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "source_document_id")
	private SourceDocument sourceDocument;

	public void addLine(DocumentLine line) {
		line.setDocument(this);
		this.lines.add(line);
	}

	public void addWithholdingTax(WithholdingTax tax) {
		tax.setDocument(this);
		this.withholdingTaxes.add(tax);
	}
}
