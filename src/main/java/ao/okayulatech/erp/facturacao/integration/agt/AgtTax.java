package ao.okayulatech.erp.facturacao.integration.agt;

import java.math.BigDecimal;

public class AgtTax {
	private String taxType;
	private String taxCountryRegion;
	private String taxCode;
	private BigDecimal percentage;
	private BigDecimal amount;

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getTaxCountryRegion() {
		return taxCountryRegion;
	}

	public void setTaxCountryRegion(String taxCountryRegion) {
		this.taxCountryRegion = taxCountryRegion;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
