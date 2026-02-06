package ao.okayulatech.erp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
	info = @Info(
		title = "Facturação Eletrónica API",
		version = "v1",
		description = "Documentação da API de facturação eletrónica.",
		contact = @Contact(name = "Okayulatech"),
		license = @License(name = "Proprietário")
	)
)
public class OpenApiConfig {}
