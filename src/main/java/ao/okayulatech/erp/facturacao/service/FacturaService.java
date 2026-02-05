package ao.okayulatech.erp.facturacao.service;

import ao.okayulatech.erp.facturacao.DTO.FacturaDTO;
import ao.okayulatech.erp.facturacao.DTO.SalesDocumentRequestDTO;

public interface FacturaService {
	FacturaDTO registarFactura(SalesDocumentRequestDTO salesDocumentRequestDTO);
}
