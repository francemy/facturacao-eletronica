package ao.okayulatech.erp.facturacao.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareInfoDTO {
	public SoftwareInfoDetailDTO softwareInfoDetail;
	public String jwsSoftwareSignature;
}
