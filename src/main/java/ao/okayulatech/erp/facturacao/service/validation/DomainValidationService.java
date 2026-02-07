package ao.okayulatech.erp.facturacao.service.validation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import ao.okayulatech.erp.facturacao.api.dto.DocumentRequest;
import ao.okayulatech.erp.facturacao.api.dto.InvoiceRequest;

@Service
public class DomainValidationService {

	public void validateInvoiceRequest(InvoiceRequest request) {
		request.getDocuments().forEach(this::validateDocumentTotals);
	}

	public void validateDocumentTotals(DocumentRequest request) {
		if (request.getTotals() == null) {
			return;
		}
		BigDecimal netTotal = BigDecimal.ZERO;
		BigDecimal taxTotal = BigDecimal.ZERO;
		for (var line : request.getLines()) {
			BigDecimal lineAmount = line.getDebitAmount();
			if (lineAmount == null && line.getQuantity() != null && line.getUnitPrice() != null) {
				lineAmount = line.getQuantity().multiply(line.getUnitPrice());
			}
			if (lineAmount != null) {
				netTotal = netTotal.add(lineAmount);
			}
			for (var tax : line.getTaxes()) {
				BigDecimal taxAmount = tax.getAmount();
				if (taxAmount == null && tax.getPercentage() != null && lineAmount != null) {
					taxAmount = lineAmount
						.multiply(tax.getPercentage())
						.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
				}
				if (taxAmount != null) {
					taxTotal = taxTotal.add(taxAmount);
				}
			}
		}
		BigDecimal grossTotal = netTotal.add(taxTotal);
		compareTotals("net", request.getTotals().getNetTotal(), netTotal);
		compareTotals("tax", request.getTotals().getTaxPayable(), taxTotal);
		compareTotals("gross", request.getTotals().getGrossTotal(), grossTotal);
	}

	private void compareTotals(String field, BigDecimal provided, BigDecimal calculated) {
		if (provided == null) {
			return;
		}
		if (provided.subtract(calculated).abs().compareTo(BigDecimal.valueOf(0.01)) > 0) {
			throw new IllegalArgumentException(
				"Total " + field + " inválido. Esperado " + calculated + " e recebido " + provided
			);
		}
	}
}
