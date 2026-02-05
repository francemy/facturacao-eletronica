package ao.okayulatech.erp.facturacao.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DocumentSaleDTO {
	private String documentNo;
	private String documentStatus;
	private String jwsDocumentSignature;

	private String documentDate;
	private String documentType;
	private String eacCode;
	private String systemEntryDate;
	private String customerTaxID;
	private String customerCountry;
	private String companyName;
	private List<LineSaleDTO> lines=new ArrayList<LineSaleDTO>();
	private List<WithholdingTaxDTO> withholdingTaxList=new ArrayList<WithholdingTaxDTO>();
	private DocumentTotalsDTO documentTotals;
}
