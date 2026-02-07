package ao.okayulatech.erp.facturacao.integration.agt;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ao.okayulatech.erp.facturacao.model.Document;
import ao.okayulatech.erp.facturacao.model.DocumentLine;
import ao.okayulatech.erp.facturacao.model.InvoiceHeader;
import ao.okayulatech.erp.facturacao.model.ReferenceInfo;
import ao.okayulatech.erp.facturacao.model.Tax;
import ao.okayulatech.erp.facturacao.model.WithholdingTax;

@Component
public class AgtMapper {

	public AgtSubmissionPayload mapSubmission(InvoiceHeader header) {
		AgtSubmissionPayload payload = new AgtSubmissionPayload();
		payload.setSubmissionUUID(header.getSubmissionUUID());
		payload.setTaxRegistrationNumber(header.getTaxRegistrationNumber());
		payload.setSubmissionTimestamp(header.getSubmissionTimestamp());
		payload.setSoftwareInfo(mapSoftwareInfo(header));
		payload.setDocuments(header.getDocuments().stream().map(this::mapDocument).collect(Collectors.toList()));
		return payload;
	}

	private AgtSoftwareInfo mapSoftwareInfo(InvoiceHeader header) {
		if (header.getSoftwareInfo() == null) {
			return null;
		}
		AgtSoftwareInfo info = new AgtSoftwareInfo();
		if (header.getSoftwareInfo().getSoftwareInfoDetail() != null) {
			AgtSoftwareInfoDetail detail = new AgtSoftwareInfoDetail();
			detail.setProductId(header.getSoftwareInfo().getSoftwareInfoDetail().getProductId());
			detail.setProductVersion(header.getSoftwareInfo().getSoftwareInfoDetail().getProductVersion());
			detail.setSoftwareValidationNumber(header.getSoftwareInfo().getSoftwareInfoDetail().getSoftwareValidationNumber());
			info.setSoftwareInfoDetail(detail);
		}
		info.setJwsSoftwareSignature(header.getSoftwareInfo().getJwsSoftwareSignature());
		return info;
	}

	private AgtDocument mapDocument(Document document) {
		AgtDocument agtDocument = new AgtDocument();
		agtDocument.setDocumentNo(document.getDocumentNo());
		agtDocument.setDocumentStatus(document.getDocumentStatus());
		agtDocument.setDocumentType(document.getDocumentType());
		agtDocument.setDocumentDate(document.getDocumentDate());
		agtDocument.setSystemEntryDate(document.getSystemEntryDate());
		agtDocument.setEacCode(document.getEacCode());
		agtDocument.setJwsDocumentSignature(document.getJwsDocumentSignature());
		agtDocument.setCustomer(mapCustomer(document));
		agtDocument.setLines(document.getLines().stream().map(this::mapLine).collect(Collectors.toList()));
		if (document.getTotals() != null) {
			AgtDocumentTotals totals = new AgtDocumentTotals();
			totals.setNetTotal(document.getTotals().getNetTotal());
			totals.setTaxPayable(document.getTotals().getTaxPayable());
			totals.setGrossTotal(document.getTotals().getGrossTotal());
			agtDocument.setTotals(totals);
		}
		agtDocument.setWithholdingTaxes(document.getWithholdingTaxes().stream()
			.map(this::mapWithholdingTax).collect(Collectors.toList()));
		return agtDocument;
	}

	private AgtCustomer mapCustomer(Document document) {
		if (document.getCustomer() == null) {
			return null;
		}
		AgtCustomer customer = new AgtCustomer();
		customer.setTaxId(document.getCustomer().getTaxId());
		customer.setCountry(document.getCustomer().getCountry());
		customer.setCompanyName(document.getCustomer().getCompanyName());
		return customer;
	}

	private AgtDocumentLine mapLine(DocumentLine line) {
		AgtDocumentLine agtLine = new AgtDocumentLine();
		agtLine.setLineNumber(line.getLineNumber());
		agtLine.setProductCode(line.getProductCode());
		agtLine.setProductDescription(line.getProductDescription());
		agtLine.setQuantity(line.getQuantity());
		agtLine.setUnitOfMeasure(line.getUnitOfMeasure());
		agtLine.setUnitPrice(line.getUnitPrice());
		agtLine.setDebitAmount(line.getDebitAmount());
		agtLine.setCreditAmount(line.getCreditAmount());
		agtLine.setSettlementAmount(line.getSettlementAmount());
		agtLine.setReferenceInfo(mapReference(line.getReferenceInfo()));
		agtLine.setTaxes(line.getTaxes().stream().map(this::mapTax).collect(Collectors.toList()));
		return agtLine;
	}

	private AgtReferenceInfo mapReference(ReferenceInfo referenceInfo) {
		if (referenceInfo == null) {
			return null;
		}
		AgtReferenceInfo info = new AgtReferenceInfo();
		info.setReference(referenceInfo.getReference());
		info.setReason(referenceInfo.getReason());
		info.setReferenceItemLineNo(referenceInfo.getReferenceItemLineNo());
		return info;
	}

	private AgtTax mapTax(Tax tax) {
		AgtTax agtTax = new AgtTax();
		agtTax.setTaxType(tax.getTaxType());
		agtTax.setTaxCountryRegion(tax.getTaxCountryRegion());
		agtTax.setTaxCode(tax.getTaxCode());
		agtTax.setPercentage(tax.getPercentage());
		agtTax.setAmount(tax.getAmount());
		return agtTax;
	}

	private AgtWithholdingTax mapWithholdingTax(WithholdingTax tax) {
		AgtWithholdingTax agtTax = new AgtWithholdingTax();
		agtTax.setType(tax.getType());
		agtTax.setDescription(tax.getDescription());
		agtTax.setAmount(tax.getAmount());
		return agtTax;
	}
}
