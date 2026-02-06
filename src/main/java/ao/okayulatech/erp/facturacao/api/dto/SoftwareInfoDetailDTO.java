package ao.okayulatech.erp.facturacao.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoftwareInfoDetailDTO {
	@NotBlank
	private String productId;
	@NotBlank
	private String productVersion;
	@NotBlank
	private String softwareValidationNumber;
}
