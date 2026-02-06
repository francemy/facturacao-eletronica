package ao.okayulatech.erp.facturacao.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ReferenceInfo {
	private String reference;
	private String reason;
	private Integer referenceItemLineNo;
}
