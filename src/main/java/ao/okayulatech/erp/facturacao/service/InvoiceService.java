package ao.okayulatech.erp.facturacao.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ao.okayulatech.erp.facturacao.api.dto.CustomerDTO;
import ao.okayulatech.erp.facturacao.api.dto.DocumentLineDTO;
import ao.okayulatech.erp.facturacao.api.dto.DocumentRequest;
import ao.okayulatech.erp.facturacao.api.dto.DocumentResponse;
import ao.okayulatech.erp.facturacao.api.dto.DocumentTotalsDTO;
import ao.okayulatech.erp.facturacao.api.dto.InvoiceRequest;
import ao.okayulatech.erp.facturacao.api.dto.InvoiceResponse;
import ao.okayulatech.erp.facturacao.api.dto.ReferenceInfoDTO;
import ao.okayulatech.erp.facturacao.api.dto.SoftwareInfoDetailDTO;
import ao.okayulatech.erp.facturacao.api.dto.SoftwareInfoDTO;
import ao.okayulatech.erp.facturacao.api.dto.TaxDTO;
import ao.okayulatech.erp.facturacao.api.dto.WithholdingTaxDTO;
import ao.okayulatech.erp.facturacao.model.Customer;
import ao.okayulatech.erp.facturacao.model.Document;
import ao.okayulatech.erp.facturacao.model.DocumentLine;
import ao.okayulatech.erp.facturacao.model.DocumentTotals;
import ao.okayulatech.erp.facturacao.model.InvoiceHeader;
import ao.okayulatech.erp.facturacao.model.ReferenceInfo;
import ao.okayulatech.erp.facturacao.model.SoftwareInfo;
import ao.okayulatech.erp.facturacao.model.SoftwareInfoDetail;
import ao.okayulatech.erp.facturacao.model.Tax;
import ao.okayulatech.erp.facturacao.model.WithholdingTax;
import ao.okayulatech.erp.facturacao.repository.DocumentRepository;
import ao.okayulatech.erp.facturacao.repository.InvoiceHeaderRepository;
import ao.okayulatech.erp.facturacao.service.crypto.SignatureService;
import ao.okayulatech.erp.facturacao.service.submission.SubmissionWorkflowService;
import ao.okayulatech.erp.facturacao.service.validation.DomainValidationService;

@Service
public class InvoiceService {

	private final InvoiceHeaderRepository invoiceHeaderRepository;
	private final DocumentRepository documentRepository;
	private final DomainValidationService domainValidationService;
	private final SignatureService signatureService;
	private final SubmissionWorkflowService submissionWorkflowService;

	public InvoiceService(
		InvoiceHeaderRepository invoiceHeaderRepository,
		DocumentRepository documentRepository,
		DomainValidationService domainValidationService,
		SignatureService signatureService,
		SubmissionWorkflowService submissionWorkflowService
	) {
		this.invoiceHeaderRepository = invoiceHeaderRepository;
		this.documentRepository = documentRepository;
		this.domainValidationService = domainValidationService;
		this.signatureService = signatureService;
		this.submissionWorkflowService = submissionWorkflowService;
	}

	@Transactional
	public InvoiceResponse createInvoice(InvoiceRequest request) {
		validateDocumentTypes(request);
		domainValidationService.validateInvoiceRequest(request);
		InvoiceHeader header = new InvoiceHeader();
		header.setSubmissionUUID(request.getSubmissionUUID());
		header.setTaxRegistrationNumber(request.getTaxRegistrationNumber());
		header.setSubmissionTimestamp(request.getSubmissionTimestamp());
		header.setSoftwareInfo(mapSoftwareInfo(request.getSoftwareInfo()));

		for (DocumentRequest documentRequest : request.getDocuments()) {
			Document document = mapDocument(documentRequest);
			applySignatureIfMissing(document);
			calculateTotals(document);
			header.addDocument(document);
		}

		InvoiceHeader saved = invoiceHeaderRepository.save(header);
		submissionWorkflowService.registerSubmission(saved);
		return mapInvoiceResponse(saved);
	}

	public List<DocumentResponse> listDocuments() {
		return documentRepository.findAll().stream().map(this::mapDocumentResponse).toList();
	}

	public Optional<DocumentResponse> getDocument(UUID id) {
		return documentRepository.findById(id).map(this::mapDocumentResponse);
	}

	public List<DocumentResponse> searchDocuments(
		String documentNo,
		String documentType,
		LocalDate dateFrom,
		LocalDate dateTo
	) {
		if (documentNo != null && !documentNo.isBlank()) {
			return documentRepository.findByDocumentNoContainingIgnoreCase(documentNo)
				.stream().map(this::mapDocumentResponse).toList();
		}
		if (documentType != null && !documentType.isBlank()) {
			return documentRepository.findByDocumentType(documentType)
				.stream().map(this::mapDocumentResponse).toList();
		}
		if (dateFrom != null && dateTo != null) {
			return documentRepository.findByDocumentDateBetween(dateFrom, dateTo)
				.stream().map(this::mapDocumentResponse).toList();
		}
		return listDocuments();
	}

	@Transactional
	public Optional<DocumentResponse> updateDocument(UUID id, DocumentRequest request) {
		return documentRepository.findById(id).map(existing -> {
			domainValidationService.validateDocumentTotals(request);
			Document updated = mapDocument(request);
			updated.setId(existing.getId());
			updated.setInvoiceHeader(existing.getInvoiceHeader());
			applySignatureIfMissing(updated);
			calculateTotals(updated);
			Document saved = documentRepository.save(updated);
			return mapDocumentResponse(saved);
		});
	}

	@Transactional
	public Optional<DocumentResponse> cancelDocument(UUID id) {
		return documentRepository.findById(id).map(document -> {
			document.setDocumentStatus("A");
			return mapDocumentResponse(document);
		});
	}

	@Transactional
	public Optional<DocumentResponse> signDocument(UUID id, String signature) {
		return documentRepository.findById(id).map(document -> {
			if (signature == null || signature.isBlank()) {
				document.setJwsDocumentSignature(signatureService.signDocument(document));
			} else {
				document.setJwsDocumentSignature(signature);
			}
			return mapDocumentResponse(document);
		});
	}

	private SoftwareInfo mapSoftwareInfo(SoftwareInfoDTO dto) {
		SoftwareInfo info = new SoftwareInfo();
		if (dto.getSoftwareInfoDetail() != null) {
			SoftwareInfoDetail detail = new SoftwareInfoDetail();
			detail.setProductId(dto.getSoftwareInfoDetail().getProductId());
			detail.setProductVersion(dto.getSoftwareInfoDetail().getProductVersion());
			detail.setSoftwareValidationNumber(dto.getSoftwareInfoDetail().getSoftwareValidationNumber());
			info.setSoftwareInfoDetail(detail);
		}
		info.setJwsSoftwareSignature(dto.getJwsSoftwareSignature());
		if (info.getJwsSoftwareSignature() == null || info.getJwsSoftwareSignature().isBlank()) {
			info.setJwsSoftwareSignature(signatureService.signSoftware(info));
		}
		return info;
	}

	private Document mapDocument(DocumentRequest request) {
		validateDocumentRequest(request);
		Document document = new Document();
		document.setDocumentNo(request.getDocumentNo());
		document.setDocumentStatus(request.getDocumentStatus());
		document.setDocumentType(request.getDocumentType());
		document.setDocumentDate(request.getDocumentDate());
		document.setSystemEntryDate(request.getSystemEntryDate());
		document.setEacCode(request.getEacCode());
		document.setJwsDocumentSignature(request.getJwsDocumentSignature());

		Customer customer = new Customer();
		customer.setTaxId(request.getCustomer().getTaxId());
		customer.setCountry(request.getCustomer().getCountry());
		customer.setCompanyName(request.getCustomer().getCompanyName());
		document.setCustomer(customer);

		request.getLines().forEach(lineRequest -> document.addLine(mapLine(lineRequest)));

		if (request.getTotals() != null) {
			DocumentTotals totals = new DocumentTotals();
			totals.setGrossTotal(request.getTotals().getGrossTotal());
			totals.setNetTotal(request.getTotals().getNetTotal());
			totals.setTaxPayable(request.getTotals().getTaxPayable());
			document.setTotals(totals);
		}

		request.getWithholdingTaxes()
			.forEach(withholdingTaxDTO -> document.addWithholdingTax(mapWithholdingTax(withholdingTaxDTO)));

		return document;
	}

	private DocumentLine mapLine(DocumentLineDTO dto) {
		DocumentLine line = new DocumentLine();
		line.setLineNumber(dto.getLineNumber());
		line.setProductCode(dto.getProductCode());
		line.setProductDescription(dto.getProductDescription());
		line.setQuantity(dto.getQuantity());
		line.setUnitOfMeasure(dto.getUnitOfMeasure());
		line.setUnitPrice(dto.getUnitPrice());
		line.setDebitAmount(dto.getDebitAmount());
		line.setCreditAmount(dto.getCreditAmount());
		line.setSettlementAmount(dto.getSettlementAmount());
		line.setReferenceInfo(mapReferenceInfo(dto.getReferenceInfo()));
		dto.getTaxes().forEach(taxDTO -> line.addTax(mapTax(taxDTO)));
		return line;
	}

	private ReferenceInfo mapReferenceInfo(ReferenceInfoDTO dto) {
		if (dto == null) {
			return null;
		}
		ReferenceInfo referenceInfo = new ReferenceInfo();
		referenceInfo.setReference(dto.getReference());
		referenceInfo.setReason(dto.getReason());
		referenceInfo.setReferenceItemLineNo(dto.getReferenceItemLineNo());
		return referenceInfo;
	}

	private Tax mapTax(TaxDTO dto) {
		Tax tax = new Tax();
		tax.setTaxType(dto.getTaxType());
		tax.setTaxCountryRegion(dto.getTaxCountryRegion());
		tax.setTaxCode(dto.getTaxCode());
		tax.setPercentage(dto.getPercentage());
		tax.setAmount(dto.getAmount());
		return tax;
	}

	private WithholdingTax mapWithholdingTax(WithholdingTaxDTO dto) {
		WithholdingTax tax = new WithholdingTax();
		tax.setType(dto.getType());
		tax.setDescription(dto.getDescription());
		tax.setAmount(dto.getAmount());
		return tax;
	}

	private void calculateTotals(Document document) {
		BigDecimal netTotal = BigDecimal.ZERO;
		BigDecimal taxTotal = BigDecimal.ZERO;

		for (DocumentLine line : document.getLines()) {
			BigDecimal lineAmount = line.getDebitAmount();
			if (lineAmount == null && line.getQuantity() != null && line.getUnitPrice() != null) {
				lineAmount = line.getQuantity().multiply(line.getUnitPrice());
				line.setDebitAmount(lineAmount);
			}
			if (lineAmount != null) {
				netTotal = netTotal.add(lineAmount);
			}
			for (Tax tax : line.getTaxes()) {
				BigDecimal taxAmount = tax.getAmount();
				if (taxAmount == null && tax.getPercentage() != null && lineAmount != null) {
					taxAmount = lineAmount
						.multiply(tax.getPercentage())
						.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
					tax.setAmount(taxAmount);
				}
				if (taxAmount != null) {
					taxTotal = taxTotal.add(taxAmount);
				}
			}
		}

		DocumentTotals totals = document.getTotals();
		if (totals == null) {
			totals = new DocumentTotals();
		}
		totals.setNetTotal(netTotal);
		totals.setTaxPayable(taxTotal);
		totals.setGrossTotal(netTotal.add(taxTotal));
		document.setTotals(totals);
	}

	private void validateDocumentTypes(InvoiceRequest request) {
		request.getDocuments().forEach(this::validateDocumentRequest);
	}

	private void validateDocumentRequest(DocumentRequest request) {
		List<String> supportedTypes = List.of("FT", "FR", "FG", "GF", "FA", "NC", "ND", "RE", "RG");
		if (!supportedTypes.contains(request.getDocumentType())) {
			throw new IllegalArgumentException("Tipo de documento inválido: " + request.getDocumentType());
		}
		if (List.of("NC", "ND", "RE").contains(request.getDocumentType())) {
			boolean missingReference = request.getLines().stream()
				.anyMatch(line -> line.getReferenceInfo() == null
					|| line.getReferenceInfo().getReference() == null
					|| line.getReferenceInfo().getReference().isBlank());
			if (missingReference) {
				throw new IllegalArgumentException("Referência obrigatória para NC/ND/RE.");
			}
		}
	}

	private void applySignatureIfMissing(Document document) {
		if (document.getJwsDocumentSignature() == null || document.getJwsDocumentSignature().isBlank()) {
			document.setJwsDocumentSignature(signatureService.signDocument(document));
		}
	}

	private InvoiceResponse mapInvoiceResponse(InvoiceHeader header) {
		InvoiceResponse response = new InvoiceResponse();
		response.setId(header.getId());
		response.setSubmissionUUID(header.getSubmissionUUID());
		response.setTaxRegistrationNumber(header.getTaxRegistrationNumber());
		response.setSubmissionTimestamp(header.getSubmissionTimestamp());
		response.setSoftwareInfo(mapSoftwareInfoResponse(header.getSoftwareInfo()));
		response.setDocuments(header.getDocuments().stream().map(this::mapDocumentResponse).toList());
		return response;
	}

	private SoftwareInfoDTO mapSoftwareInfoResponse(SoftwareInfo info) {
		if (info == null) {
			return null;
		}
		SoftwareInfoDTO dto = new SoftwareInfoDTO();
		SoftwareInfoDetail detail = info.getSoftwareInfoDetail();
		if (detail != null) {
			SoftwareInfoDetailDTO detailDTO = new SoftwareInfoDetailDTO();
			detailDTO.setProductId(detail.getProductId());
			detailDTO.setProductVersion(detail.getProductVersion());
			detailDTO.setSoftwareValidationNumber(detail.getSoftwareValidationNumber());
			dto.setSoftwareInfoDetail(detailDTO);
		}
		dto.setJwsSoftwareSignature(info.getJwsSoftwareSignature());
		return dto;
	}

	private DocumentResponse mapDocumentResponse(Document document) {
		DocumentResponse response = new DocumentResponse();
		response.setId(document.getId());
		response.setDocumentNo(document.getDocumentNo());
		response.setDocumentStatus(document.getDocumentStatus());
		response.setDocumentType(document.getDocumentType());
		response.setDocumentDate(document.getDocumentDate());
		response.setSystemEntryDate(document.getSystemEntryDate());
		response.setEacCode(document.getEacCode());
		response.setJwsDocumentSignature(document.getJwsDocumentSignature());
		response.setCustomer(mapCustomer(document.getCustomer()));
		response.setLines(document.getLines().stream().map(this::mapLineResponse).toList());
		response.setTotals(mapTotalsResponse(document.getTotals()));
		response.setWithholdingTaxes(document.getWithholdingTaxes().stream().map(this::mapWithholdingTaxResponse).toList());
		return response;
	}

	private CustomerDTO mapCustomer(Customer customer) {
		if (customer == null) {
			return null;
		}
		CustomerDTO dto = new CustomerDTO();
		dto.setTaxId(customer.getTaxId());
		dto.setCountry(customer.getCountry());
		dto.setCompanyName(customer.getCompanyName());
		return dto;
	}

	private DocumentLineDTO mapLineResponse(DocumentLine line) {
		DocumentLineDTO dto = new DocumentLineDTO();
		dto.setLineNumber(line.getLineNumber());
		dto.setProductCode(line.getProductCode());
		dto.setProductDescription(line.getProductDescription());
		dto.setQuantity(line.getQuantity());
		dto.setUnitOfMeasure(line.getUnitOfMeasure());
		dto.setUnitPrice(line.getUnitPrice());
		dto.setDebitAmount(line.getDebitAmount());
		dto.setCreditAmount(line.getCreditAmount());
		dto.setSettlementAmount(line.getSettlementAmount());
		dto.setReferenceInfo(mapReferenceInfoResponse(line.getReferenceInfo()));
		dto.setTaxes(line.getTaxes().stream().map(this::mapTaxResponse).toList());
		return dto;
	}

	private ReferenceInfoDTO mapReferenceInfoResponse(ReferenceInfo referenceInfo) {
		if (referenceInfo == null) {
			return null;
		}
		ReferenceInfoDTO dto = new ReferenceInfoDTO();
		dto.setReference(referenceInfo.getReference());
		dto.setReason(referenceInfo.getReason());
		dto.setReferenceItemLineNo(referenceInfo.getReferenceItemLineNo());
		return dto;
	}

	private TaxDTO mapTaxResponse(Tax tax) {
		TaxDTO dto = new TaxDTO();
		dto.setTaxType(tax.getTaxType());
		dto.setTaxCountryRegion(tax.getTaxCountryRegion());
		dto.setTaxCode(tax.getTaxCode());
		dto.setPercentage(tax.getPercentage());
		dto.setAmount(tax.getAmount());
		return dto;
	}

	private WithholdingTaxDTO mapWithholdingTaxResponse(WithholdingTax tax) {
		WithholdingTaxDTO dto = new WithholdingTaxDTO();
		dto.setType(tax.getType());
		dto.setDescription(tax.getDescription());
		dto.setAmount(tax.getAmount());
		return dto;
	}

	private DocumentTotalsDTO mapTotalsResponse(DocumentTotals totals) {
		if (totals == null) {
			return null;
		}
		DocumentTotalsDTO dto = new DocumentTotalsDTO();
		dto.setNetTotal(totals.getNetTotal());
		dto.setTaxPayable(totals.getTaxPayable());
		dto.setGrossTotal(totals.getGrossTotal());
		return dto;
	}
}
