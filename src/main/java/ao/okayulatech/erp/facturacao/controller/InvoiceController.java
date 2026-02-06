package ao.okayulatech.erp.facturacao.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ao.okayulatech.erp.facturacao.api.dto.DocumentResponse;
import ao.okayulatech.erp.facturacao.api.dto.InvoiceRequest;
import ao.okayulatech.erp.facturacao.api.dto.InvoiceResponse;
import ao.okayulatech.erp.facturacao.api.dto.SignRequest;
import ao.okayulatech.erp.facturacao.service.InvoiceService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

	private final InvoiceService invoiceService;

	public InvoiceController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@PostMapping
	public ResponseEntity<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.createInvoice(request));
	}

	@GetMapping
	public ResponseEntity<List<DocumentResponse>> listDocuments() {
		return ResponseEntity.ok(invoiceService.listDocuments());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DocumentResponse> getDocument(@PathVariable UUID id) {
		return invoiceService.getDocument(id)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/search")
	public ResponseEntity<List<DocumentResponse>> searchDocuments(
		@RequestParam(required = false) String documentNo,
		@RequestParam(required = false) String documentType,
		@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateFrom,
		@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dateTo
	) {
		return ResponseEntity.ok(invoiceService.searchDocuments(documentNo, documentType, dateFrom, dateTo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<DocumentResponse> updateDocument(
		@PathVariable UUID id,
		@Valid @RequestBody InvoiceRequest request
	) {
		if (request.getDocuments().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		return invoiceService.updateDocument(id, request.getDocuments().get(0))
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<DocumentResponse> cancelDocument(@PathVariable UUID id) {
		return invoiceService.cancelDocument(id)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{id}/sign")
	public ResponseEntity<DocumentResponse> signDocument(
		@PathVariable UUID id,
		@Valid @RequestBody SignRequest request
	) {
		return invoiceService.signDocument(id, request.getSignature())
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/export/saft")
	public ResponseEntity<String> exportSaft(@PathVariable UUID id) {
		if (invoiceService.getDocument(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
			.body("Exportação SAF-T em desenvolvimento.");
	}

	@GetMapping("/{id}/pdf")
	public ResponseEntity<String> exportPdf(@PathVariable UUID id) {
		if (invoiceService.getDocument(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
			.body("Geração de PDF em desenvolvimento.");
	}

	@PostMapping("/credit-note")
	public ResponseEntity<InvoiceResponse> createCreditNote(@Valid @RequestBody InvoiceRequest request) {
		return validateTypeAndCreate(request, "NC");
	}

	@PostMapping("/debit-note")
	public ResponseEntity<InvoiceResponse> createDebitNote(@Valid @RequestBody InvoiceRequest request) {
		return validateTypeAndCreate(request, "ND");
	}

	@PostMapping("/receipt")
	public ResponseEntity<InvoiceResponse> createReceipt(@Valid @RequestBody InvoiceRequest request) {
		return validateTypeAndCreate(request, "RG");
	}

	@PostMapping("/reversal")
	public ResponseEntity<InvoiceResponse> createReversal(@Valid @RequestBody InvoiceRequest request) {
		return validateTypeAndCreate(request, "RE");
	}

	@PostMapping("/advance")
	public ResponseEntity<InvoiceResponse> createAdvance(@Valid @RequestBody InvoiceRequest request) {
		return validateTypeAndCreate(request, "FA");
	}

	private ResponseEntity<InvoiceResponse> validateTypeAndCreate(InvoiceRequest request, String requiredType) {
		boolean matches = request.getDocuments().stream()
			.allMatch(document -> requiredType.equalsIgnoreCase(document.getDocumentType()));
		if (!matches) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.createInvoice(request));
	}
}
