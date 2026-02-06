package ao.okayulatech.erp.facturacao.model;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DocumentTotals {
	private BigDecimal taxPayable;
	private BigDecimal netTotal;
	private BigDecimal grossTotal;
}
