package ao.okayulatech.erp.facturacao.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ao.okayulatech.erp.facturacao.model.submission.Submission;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice_headers")
public class InvoiceHeader {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String submissionUUID;
	private String taxRegistrationNumber;
	private LocalDateTime submissionTimestamp;

	@OneToOne(mappedBy = "invoiceHeader", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private SoftwareInfo softwareInfo;

	@OneToOne(mappedBy = "invoiceHeader", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Submission submission;

	@OneToMany(mappedBy = "invoiceHeader", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Document> documents = new ArrayList<>();

	public void addDocument(Document document) {
		document.setInvoiceHeader(this);
		this.documents.add(document);
	}

	public void setSoftwareInfo(SoftwareInfo info) {
		if (info != null) {
			info.setInvoiceHeader(this);
		}
		this.softwareInfo = info;
	}
}
