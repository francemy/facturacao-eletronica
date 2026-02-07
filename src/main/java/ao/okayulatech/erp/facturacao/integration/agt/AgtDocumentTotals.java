package ao.okayulatech.erp.facturacao.integration.agt;

import java.math.BigDecimal;

public class AgtDocumentTotals {
	private BigDecimal netTotal;
	private BigDecimal taxPayable;
	private BigDecimal grossTotal;

	public BigDecimal getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(BigDecimal netTotal) {
		this.netTotal = netTotal;
	}

	public BigDecimal getTaxPayable() {
		return taxPayable;
	}

	public void setTaxPayable(BigDecimal taxPayable) {
		this.taxPayable = taxPayable;
	}

	public BigDecimal getGrossTotal() {
		return grossTotal;
	}

	public void setGrossTotal(BigDecimal grossTotal) {
		this.grossTotal = grossTotal;
	}
}
