package ao.okayulatech.erp.facturacao.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentLineDTO {
	@NotNull
	private Integer lineNumber;
	@NotBlank
	private String productCode;
	@NotBlank
	private String productDescription;
	@NotNull
	private BigDecimal quantity;
	@NotBlank
	private String unitOfMeasure;
	@NotNull
	private BigDecimal unitPrice;
	private BigDecimal debitAmount;
	private BigDecimal creditAmount;
	private BigDecimal settlementAmount;
	@Valid
	private ReferenceInfoDTO referenceInfo;
	@Valid
	private List<TaxDTO> taxes = new ArrayList<>();
}
