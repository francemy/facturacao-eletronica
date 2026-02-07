package ao.okayulatech.erp.facturacao.integration.agt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(AgtProperties.class)
public class AgtConfig {

	@Bean
	public RestClient agtRestClient(RestClient.Builder builder, AgtProperties properties) {
		return builder
			.baseUrl(properties.getBaseUrl())
			.build();
	}
}
