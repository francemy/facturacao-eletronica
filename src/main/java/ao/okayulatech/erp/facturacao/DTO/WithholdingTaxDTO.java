package ao.okayulatech.erp.facturacao.DTO;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithholdingTaxDTO {
	public String withholdingTaxType;
	public String withholdingTaxDescription;
	public BigDecimal withholdingTaxAmount;
}
