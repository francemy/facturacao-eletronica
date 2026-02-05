package ao.okayulatech.erp.facturacao.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacturaDTO {
	private String documentNo;
	private String documentStatus;
	private String jwsDocumentSignature;
	private LocalDate documentDate;
	private LocalDateTime systemEntryDate;
	private String customerTaxID;
	private String customerCountry="AO";
	private String companyName;
	private String documentType;
	private String empresaFacturacao;
	
	public FacturaDTO(DocumentSaleDTO document) {
		this.documentNo=document.getDocumentNo();
		this.companyName=document.getCompanyName();
		this.customerCountry=document.getCustomerCountry();
		this.customerTaxID=document.getCustomerTaxID();
		this.documentDate=LocalDate.parse(document.getDocumentDate());
		this.documentNo=document.getDocumentNo();
		this.documentStatus=document.getDocumentStatus();
		this.documentType=document.getDocumentType();
		this.empresaFacturacao=document.getCompanyName();
		this.jwsDocumentSignature=document.getJwsDocumentSignature();
		this.systemEntryDate=LocalDateTime.ofInstant(java.time.Instant.parse(document.getSystemEntryDate()),ZoneOffset.UTC);
	}
	
	

	@Override
	public String toString() {
		return "FacturaDTO [documentNo=" + documentNo + ", documentStatus=" + documentStatus + ", jwsDocumentSignature="
				+ jwsDocumentSignature + ", documentDate=" + documentDate + ", systemEntryDate=" + systemEntryDate
				+ ", customerTaxID=" + customerTaxID + ", customerCountry=" + customerCountry + ", companyName="
				+ companyName + ", documentType=" + documentType + ", empresaFacturacao=" + empresaFacturacao + "]";
	}

	
}
