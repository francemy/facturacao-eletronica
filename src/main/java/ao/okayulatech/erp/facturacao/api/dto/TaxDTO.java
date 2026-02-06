package ao.okayulatech.erp.facturacao.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxDTO {
	@NotBlank
	private String taxType;
	@NotBlank
	private String taxCountryRegion;
	@NotBlank
	private String taxCode;
	@NotNull
	private BigDecimal percentage;
	@NotNull
	private BigDecimal amount;
}
