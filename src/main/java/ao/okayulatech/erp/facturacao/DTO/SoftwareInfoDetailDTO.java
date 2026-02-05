package ao.okayulatech.erp.facturacao.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareInfoDetailDTO {
	public String productId;
	public String productVersion;
	public String softwareValidationNumber;
}
