package ao.okayulatech.erp.facturacao.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoftwareInfoDTO {
	@Valid
	@NotNull
	private SoftwareInfoDetailDTO softwareInfoDetail;

	private String jwsSoftwareSignature;
}
