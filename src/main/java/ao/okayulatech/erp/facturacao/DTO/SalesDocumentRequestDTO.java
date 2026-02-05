package ao.okayulatech.erp.facturacao.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SalesDocumentRequestDTO {
	public String schemaVersion;
	public String submissionUUID;
	public String taxRegistrationNumber;
	public String submissionTimeStamp;
	public SoftwareInfoDTO softwareInfo;
	public Integer numberOfEntries;
	public List<DocumentSaleDTO> documents = new ArrayList<DocumentSaleDTO>();
}
