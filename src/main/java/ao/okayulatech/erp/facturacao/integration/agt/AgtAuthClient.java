package ao.okayulatech.erp.facturacao.integration.agt;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AgtAuthClient {

	private final RestClient restClient;
	private final AgtProperties properties;

	public AgtAuthClient(RestClient.Builder builder, AgtProperties properties) {
		this.properties = properties;
		this.restClient = builder.baseUrl(properties.getAuthUrl()).build();
	}

	public String fetchToken() {
		Map<String, Object> response = restClient.post()
			.uri("/token")
			.contentType(MediaType.APPLICATION_JSON)
			.body(Map.of(
				"clientId", properties.getClientId(),
				"clientSecret", properties.getClientSecret()
			))
			.retrieve()
			.body(Map.class);
		if (response == null || response.get("access_token") == null) {
			throw new IllegalStateException("Token da AGT não retornado.");
		}
		return response.get("access_token").toString();
	}
}
