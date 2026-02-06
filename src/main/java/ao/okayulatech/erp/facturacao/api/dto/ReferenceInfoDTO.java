package ao.okayulatech.erp.facturacao.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceInfoDTO {
	private String reference;
	private String reason;
	private Integer referenceItemLineNo;
}
