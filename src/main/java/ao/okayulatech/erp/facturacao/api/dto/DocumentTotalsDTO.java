package ao.okayulatech.erp.facturacao.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentTotalsDTO {
	private BigDecimal taxPayable;
	private BigDecimal netTotal;
	private BigDecimal grossTotal;
}
