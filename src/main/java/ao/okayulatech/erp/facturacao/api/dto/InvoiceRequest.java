package ao.okayulatech.erp.facturacao.api.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequest {
	@NotBlank
	private String submissionUUID;
	@NotBlank
	@Pattern(regexp = "\\d{9,10}")
	private String taxRegistrationNumber;
	@NotNull
	private LocalDateTime submissionTimestamp;
	@Valid
	@NotNull
	private SoftwareInfoDTO softwareInfo;
	@Valid
	@NotNull
	private List<DocumentRequest> documents = new ArrayList<>();
}
