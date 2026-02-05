package ao.okayulatech.erp.facturacao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="TB_FACTURA")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name="DOCUMENTO_NO",length = 100)
	private String documentNo;
	@Column(name="DOCUMENT_STATUS",length = 100)
	private String documentStatus;
	@Column(name="JWS_DOCUMENT_SIGNATURE",length = 4000)
	private String jwsDocumentSignature;
	@Column(name="DOCUMENT_DATE")
	private LocalDate documentDate=LocalDate.now();
	@Column(name="SYSTEM_ENTRY_DATE")
	private LocalDateTime systemEntryDate=LocalDateTime.now();
	@Column(name="CUSTOMER_TAX_ID",length = 50)
	private String customerTaxID;
	@Column(name="CUSTOMER_COUNTRY",length = 50)
	private String customerCountry="AO";
	@Column(name="COMPANY_NAME",length = 200)
	private String companyName;
	@ManyToOne
	@JoinColumn(name="TIPO_FACTURA_ID")
	private TipoFactura documentType;
	@ManyToOne
	@JoinColumn(name="EMPRESA_FACTURACAO_ID")
	private EmpresaFacturacao empresaFacturacao;
	@Override
	public String toString() {
		return "Factura [id=" + id + ", documentNo=" + documentNo + ", documentStatus=" + documentStatus
				+ ", jwsDocumentSignature=" + jwsDocumentSignature + ", documentDate=" + documentDate
				+ ", systemEntryDate=" + systemEntryDate + ", customerTaxID=" + customerTaxID + ", customerCountry="
				+ customerCountry + ", companyName=" + companyName + ", documentType=" + documentType
				+ ", empresaFacturacao=" + empresaFacturacao + "]";
	}
	
	
}
