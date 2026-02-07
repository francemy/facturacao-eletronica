package ao.okayulatech.erp.facturacao.service.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ao.okayulatech.erp.facturacao.api.dto.CustomerDTO;
import ao.okayulatech.erp.facturacao.api.dto.DocumentLineDTO;
import ao.okayulatech.erp.facturacao.api.dto.DocumentRequest;
import ao.okayulatech.erp.facturacao.api.dto.DocumentTotalsDTO;
import ao.okayulatech.erp.facturacao.api.dto.TaxDTO;

class DomainValidationServiceTest {
	private final DomainValidationService service = new DomainValidationService();

	@Test
	void acceptsMatchingTotals() {
		DocumentRequest request = buildDocument(BigDecimal.valueOf(100), BigDecimal.valueOf(14));
		request.setTotals(buildTotals(BigDecimal.valueOf(100), BigDecimal.valueOf(14)));
		assertDoesNotThrow(() -> service.validateDocumentTotals(request));
	}

	@Test
	void rejectsMismatchedTotals() {
		DocumentRequest request = buildDocument(BigDecimal.valueOf(100), BigDecimal.valueOf(14));
		request.setTotals(buildTotals(BigDecimal.valueOf(90), BigDecimal.valueOf(14)));
		assertThrows(IllegalArgumentException.class, () -> service.validateDocumentTotals(request));
	}

	private DocumentRequest buildDocument(BigDecimal net, BigDecimal tax) {
		DocumentRequest request = new DocumentRequest();
		request.setDocumentNo("FT 1/24");
		request.setDocumentStatus("N");
		request.setDocumentType("FT");
		request.setDocumentDate(LocalDate.now());
		request.setSystemEntryDate(LocalDateTime.now());
		CustomerDTO customer = new CustomerDTO();
		customer.setTaxId("123456789");
		customer.setCountry("AO");
		customer.setCompanyName("Cliente");
		request.setCustomer(customer);

		DocumentLineDTO line = new DocumentLineDTO();
		line.setLineNumber("1");
		line.setProductCode("PRD");
		line.setProductDescription("Produto");
		line.setQuantity(BigDecimal.ONE);
		line.setUnitPrice(net);
		line.setDebitAmount(net);

		TaxDTO taxDTO = new TaxDTO();
		taxDTO.setTaxType("IVA");
		taxDTO.setTaxCountryRegion("AO");
		taxDTO.setTaxCode("IVA");
		taxDTO.setAmount(tax);
		line.getTaxes().add(taxDTO);
		request.getLines().add(line);

		return request;
	}

	private DocumentTotalsDTO buildTotals(BigDecimal net, BigDecimal tax) {
		DocumentTotalsDTO totals = new DocumentTotalsDTO();
		totals.setNetTotal(net);
		totals.setTaxPayable(tax);
		totals.setGrossTotal(net.add(tax));
		return totals;
	}
}
