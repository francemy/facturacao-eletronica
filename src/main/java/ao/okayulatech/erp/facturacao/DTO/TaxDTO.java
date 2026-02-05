package ao.okayulatech.erp.facturacao.DTO;

import java.math.BigDecimal;
import lombok.*;


@Getter
@Setter
public class TaxDTO {
	public String taxType;
	public String taxCountryRegion;
	public String taxCode;
	public BigDecimal taxPercentage;
	public BigDecimal taxContribution;
}
