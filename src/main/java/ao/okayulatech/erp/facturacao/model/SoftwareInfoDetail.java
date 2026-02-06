package ao.okayulatech.erp.facturacao.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SoftwareInfoDetail {
	private String productId;
	private String productVersion;
	private String softwareValidationNumber;
}
