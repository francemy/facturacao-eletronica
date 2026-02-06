package ao.okayulatech.erp.facturacao.model;

import jakarta.persistence.Embedded;
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
@Table(name = "software_info")
public class SoftwareInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private java.util.UUID id;

	@Embedded
	private SoftwareInfoDetail softwareInfoDetail;

	private String jwsSoftwareSignature;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_header_id")
	private InvoiceHeader invoiceHeader;
}
