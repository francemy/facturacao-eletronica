package ao.okayulatech.erp.facturacao.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignRequest {
	@NotBlank
	private String signature;
}
