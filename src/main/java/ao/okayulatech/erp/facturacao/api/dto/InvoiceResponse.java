package ao.okayulatech.erp.facturacao.api.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponse {
	private UUID id;
	private String submissionUUID;
	private String taxRegistrationNumber;
	private LocalDateTime submissionTimestamp;
	private SoftwareInfoDTO softwareInfo;
	private List<DocumentResponse> documents = new ArrayList<>();
}
