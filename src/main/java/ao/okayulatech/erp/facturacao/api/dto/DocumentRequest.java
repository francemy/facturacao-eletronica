package ao.okayulatech.erp.facturacao.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {
	@NotBlank
	private String documentNo;
	@NotBlank
	private String documentStatus;
	@NotBlank
	private String documentType;
	@NotNull
	private LocalDate documentDate;
	@NotNull
	private LocalDateTime systemEntryDate;
	private String eacCode;
	@Valid
	@NotNull
	private CustomerDTO customer;
	@Valid
	private List<DocumentLineDTO> lines = new ArrayList<>();
	@Valid
	private DocumentTotalsDTO totals;
	@Valid
	private List<WithholdingTaxDTO> withholdingTaxes = new ArrayList<>();
	private String jwsDocumentSignature;
}
