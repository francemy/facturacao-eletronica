package ao.okayulatech.erp.facturacao.DTO;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTotalsDTO {
	public BigDecimal taxPayable;
	public BigDecimal netTotal;
	public BigDecimal grossTotal;
}
