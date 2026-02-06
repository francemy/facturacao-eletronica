package ao.okayulatech.erp.facturacao.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithholdingTaxDTO {
	@NotBlank
	private String type;
	@NotBlank
	private String description;
	@NotNull
	private BigDecimal amount;
}
