package ao.okayulatech.erp.facturacao.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.okayulatech.erp.facturacao.DTO.DocumentSaleDTO;
import ao.okayulatech.erp.facturacao.DTO.FacturaDTO;
import ao.okayulatech.erp.facturacao.DTO.SalesDocumentRequestDTO;
import ao.okayulatech.erp.facturacao.model.EmpresaFacturacao;
import ao.okayulatech.erp.facturacao.model.Factura;
import ao.okayulatech.erp.facturacao.model.TipoFactura;
import ao.okayulatech.erp.facturacao.repository.FacturaRepository;
import ao.okayulatech.erp.facturacao.repository.TipoFacturaRepository;
import ao.okayulatech.erp.facturacao.service.FacturaService;

@Service
public class FacturaServiceImpl implements FacturaService {
	private ModelMapper modelMapper = new ModelMapper();
	private @Autowired FacturaRepository facturaRepository;
	private @Autowired TipoFacturaRepository tipoFacturaRepository;
	


	@Override
	public FacturaDTO registarFactura(SalesDocumentRequestDTO salesDocumentRequestDTO) {
		DocumentSaleDTO FacturaDTO=null;
		FacturaDTO facturaDTO     =null;

		for (DocumentSaleDTO documentSaleDTO : salesDocumentRequestDTO.getDocuments()) {
			TipoFactura tipoFactura = this.tipoFacturaRepository.BUSCAR_TIPO_DE_FACTURAS(documentSaleDTO.getDocumentType());
			
			facturaDTO = new FacturaDTO(documentSaleDTO);
			
			Factura factura = modelMapper.map(facturaDTO, Factura.class);
			factura.setEmpresaFacturacao(new EmpresaFacturacao(salesDocumentRequestDTO.getTaxRegistrationNumber()));
			factura.setDocumentType(tipoFactura);
			factura.setId(null);
			
			
			
			
			this.facturaRepository.save(factura);
			FacturaDTO=documentSaleDTO;
		}
		
		return new FacturaDTO(FacturaDTO);
	}
}
