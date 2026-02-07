package ao.okayulatech.erp.facturacao.service.crypto;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ao.okayulatech.erp.facturacao.model.Document;
import ao.okayulatech.erp.facturacao.model.DocumentLine;
import ao.okayulatech.erp.facturacao.model.SoftwareInfo;
import ao.okayulatech.erp.facturacao.model.Tax;
import ao.okayulatech.erp.facturacao.model.WithholdingTax;

@Service
public class SignatureService {
	private final JwsService jwsService;
	private final ObjectMapper objectMapper;

	public SignatureService(JwsService jwsService, ObjectMapper objectMapper) {
		this.jwsService = jwsService;
		this.objectMapper = objectMapper;
	}

	public String signDocument(Document document) {
		Map<String, Object> payload = new HashMap<>();
		payload.put("documentNo", document.getDocumentNo());
		payload.put("documentStatus", document.getDocumentStatus());
		payload.put("documentType", document.getDocumentType());
		payload.put("documentDate", document.getDocumentDate());
		payload.put("systemEntryDate", document.getSystemEntryDate());
		payload.put("eacCode", document.getEacCode());
		payload.put("totals", document.getTotals());
		payload.put("customer", document.getCustomer());
		payload.put("lines", mapLines(document.getLines()));
		payload.put("withholdingTaxes", mapWithholdingTaxes(document.getWithholdingTaxes()));
		return signObject(payload);
	}

	public String signSoftware(SoftwareInfo info) {
		Map<String, Object> payload = new HashMap<>();
		payload.put("softwareInfoDetail", info.getSoftwareInfoDetail());
		payload.put("jwsSoftwareSignature", info.getJwsSoftwareSignature());
		return signObject(payload);
	}

	private String signObject(Object object) {
		try {
			String payload = objectMapper.writeValueAsString(object);
			return jwsService.signPayload(payload);
		} catch (Exception ex) {
			throw new IllegalStateException("Falha ao gerar assinatura.", ex);
		}
	}

	private List<Map<String, Object>> mapLines(List<DocumentLine> lines) {
		return lines.stream()
			.map(line -> {
				Map<String, Object> payload = new HashMap<>();
				payload.put("lineNumber", line.getLineNumber());
				payload.put("productCode", line.getProductCode());
				payload.put("productDescription", line.getProductDescription());
				payload.put("quantity", line.getQuantity());
				payload.put("unitOfMeasure", line.getUnitOfMeasure());
				payload.put("unitPrice", line.getUnitPrice());
				payload.put("debitAmount", line.getDebitAmount());
				payload.put("creditAmount", line.getCreditAmount());
				payload.put("settlementAmount", line.getSettlementAmount());
				payload.put("referenceInfo", line.getReferenceInfo());
				payload.put("taxes", mapTaxes(line.getTaxes()));
				return payload;
			}).toList();
	}

	private List<Map<String, Object>> mapTaxes(List<Tax> taxes) {
		return taxes.stream()
			.map(tax -> {
				Map<String, Object> payload = new HashMap<>();
				payload.put("taxType", tax.getTaxType());
				payload.put("taxCountryRegion", tax.getTaxCountryRegion());
				payload.put("taxCode", tax.getTaxCode());
				payload.put("percentage", tax.getPercentage());
				payload.put("amount", tax.getAmount());
				return payload;
			}).toList();
	}

	private List<Map<String, Object>> mapWithholdingTaxes(List<WithholdingTax> taxes) {
		return taxes.stream()
			.map(tax -> {
				Map<String, Object> payload = new HashMap<>();
				payload.put("type", tax.getType());
				payload.put("description", tax.getDescription());
				payload.put("amount", tax.getAmount());
				return payload;
			}).toList();
	}
}
