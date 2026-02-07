package ao.okayulatech.erp.facturacao.integration.agt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgtDocument {
	private String documentNo;
	private String documentStatus;
	private String documentType;
	private LocalDate documentDate;
	private LocalDateTime systemEntryDate;
	private String eacCode;
	private String jwsDocumentSignature;
	private AgtCustomer customer;
	private List<AgtDocumentLine> lines = new ArrayList<>();
	private AgtDocumentTotals totals;
	private List<AgtWithholdingTax> withholdingTaxes = new ArrayList<>();

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public LocalDate getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(LocalDate documentDate) {
		this.documentDate = documentDate;
	}

	public LocalDateTime getSystemEntryDate() {
		return systemEntryDate;
	}

	public void setSystemEntryDate(LocalDateTime systemEntryDate) {
		this.systemEntryDate = systemEntryDate;
	}

	public String getEacCode() {
		return eacCode;
	}

	public void setEacCode(String eacCode) {
		this.eacCode = eacCode;
	}

	public String getJwsDocumentSignature() {
		return jwsDocumentSignature;
	}

	public void setJwsDocumentSignature(String jwsDocumentSignature) {
		this.jwsDocumentSignature = jwsDocumentSignature;
	}

	public AgtCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(AgtCustomer customer) {
		this.customer = customer;
	}

	public List<AgtDocumentLine> getLines() {
		return lines;
	}

	public void setLines(List<AgtDocumentLine> lines) {
		this.lines = lines;
	}

	public AgtDocumentTotals getTotals() {
		return totals;
	}

	public void setTotals(AgtDocumentTotals totals) {
		this.totals = totals;
	}

	public List<AgtWithholdingTax> getWithholdingTaxes() {
		return withholdingTaxes;
	}

	public void setWithholdingTaxes(List<AgtWithholdingTax> withholdingTaxes) {
		this.withholdingTaxes = withholdingTaxes;
	}
}
