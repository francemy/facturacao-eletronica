package ao.okayulatech.erp.facturacao.integration.agt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class AgtClient {
	private static final Logger logger = LoggerFactory.getLogger(AgtClient.class);

	private final RestClient restClient;
	private final AgtAuthClient authClient;
	private final AgtErrorTranslator errorTranslator;

	public AgtClient(RestClient agtRestClient, AgtAuthClient authClient, AgtErrorTranslator errorTranslator) {
		this.restClient = agtRestClient;
		this.authClient = authClient;
		this.errorTranslator = errorTranslator;
	}

	public AgtSubmissionResponse submit(AgtSubmissionPayload payload) {
		String token = authClient.fetchToken();
		try {
			return restClient.post()
				.uri("/submissions")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.body(payload)
				.retrieve()
				.onStatus(HttpStatusCode::isError, (request, response) -> {
					throw errorTranslator.translate(response.getStatusCode(), response);
				})
				.body(AgtSubmissionResponse.class);
		} catch (RestClientResponseException ex) {
			logger.error("Erro ao enviar submissão para AGT: {}", ex.getResponseBodyAsString(), ex);
			throw errorTranslator.translate(ex);
		}
	}
}
