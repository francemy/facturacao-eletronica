package ao.okayulatech.erp.facturacao.integration.agt;

import java.math.BigDecimal;

public class AgtWithholdingTax {
	private String type;
	private String description;
	private BigDecimal amount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
