package ao.okayulatech.erp.facturacao.service.crypto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import ao.okayulatech.erp.facturacao.model.Document;

class SignatureServiceTest {

	@Test
	void signsDocumentPayload() {
		SignatureProperties properties = new SignatureProperties();
		KeyStoreService keyStoreService = new KeyStoreService(properties, new DefaultResourceLoader());
		JwsService jwsService = new JwsService(keyStoreService, properties);
		SignatureService signatureService = new SignatureService(jwsService, new ObjectMapper());

		Document document = new Document();
		document.setDocumentNo("FT 1/24");

		String signature = signatureService.signDocument(document);
		assertNotNull(signature);
	}
}
