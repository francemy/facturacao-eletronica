package ao.okayulatech.erp.facturacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ao.okayulatech.erp.facturacao.DTO.FacturaDTO;
import ao.okayulatech.erp.facturacao.DTO.SalesDocumentRequestDTO;
import ao.okayulatech.erp.facturacao.service.FacturaService;

@RestController
@RequestMapping("/fatura-geral/v1")
@CrossOrigin(origins = "*")
public class ControllerEmissaoFactura {
	
	private @Autowired FacturaService facturaService;
	
	@PostMapping("/registar")
	public FacturaDTO registarFactura(@RequestBody SalesDocumentRequestDTO salesDocumentRequestDTO) {
		
		Gson gson=new Gson();
		
		String json = gson.toJson(salesDocumentRequestDTO);
		
		System.err.println(json);
		
		FacturaDTO registarFactura = this.facturaService.registarFactura(salesDocumentRequestDTO);
		
		return registarFactura;
	}

}
