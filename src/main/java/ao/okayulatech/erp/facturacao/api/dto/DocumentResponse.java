package ao.okayulatech.erp.facturacao.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponse {
	private UUID id;
	private String documentNo;
	private String documentStatus;
	private String documentType;
	private LocalDate documentDate;
	private LocalDateTime systemEntryDate;
	private String eacCode;
	private CustomerDTO customer;
	private List<DocumentLineDTO> lines = new ArrayList<>();
	private DocumentTotalsDTO totals;
	private List<WithholdingTaxDTO> withholdingTaxes = new ArrayList<>();
	private String jwsDocumentSignature;
}
