package ao.okayulatech.erp.facturacao.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
	@NotBlank
	@Pattern(regexp = "\\d{9,10}")
	private String taxId;

	@NotBlank
	private String country;

	@NotBlank
	private String companyName;
}
