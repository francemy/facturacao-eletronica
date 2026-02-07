package ao.okayulatech.erp.facturacao.integration.agt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AgtDocumentLine {
	private String lineNumber;
	private String productCode;
	private String productDescription;
	private BigDecimal quantity;
	private String unitOfMeasure;
	private BigDecimal unitPrice;
	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private BigDecimal settlementAmount;
	private AgtReferenceInfo referenceInfo;
	private List<AgtTax> taxes = new ArrayList<>();

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public AgtReferenceInfo getReferenceInfo() {
		return referenceInfo;
	}

	public void setReferenceInfo(AgtReferenceInfo referenceInfo) {
		this.referenceInfo = referenceInfo;
	}

	public List<AgtTax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<AgtTax> taxes) {
		this.taxes = taxes;
	}
}
