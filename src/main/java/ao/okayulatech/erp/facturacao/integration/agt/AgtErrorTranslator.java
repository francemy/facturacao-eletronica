package ao.okayulatech.erp.facturacao.integration.agt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class AgtErrorTranslator {

	public RuntimeException translate(HttpStatusCode status, ClientHttpResponse response) throws IOException {
		String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
		return new IllegalStateException("Erro AGT [" + status.value() + "]: " + body);
	}

	public RuntimeException translate(RestClientResponseException ex) {
		return new IllegalStateException("Erro AGT [" + ex.getRawStatusCode() + "]: " + ex.getResponseBodyAsString(), ex);
	}
}
