package ao.okayulatech.erp.facturacao.integration.agt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "agt")
public class AgtProperties {
	private String baseUrl;
	private String authUrl;
	private String clientId;
	private String clientSecret;
	private int timeoutSeconds = 30;
	private int outboxIntervalMs = 5000;
	private int maxRetries = 5;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public int getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public void setTimeoutSeconds(int timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
	}

	public int getOutboxIntervalMs() {
		return outboxIntervalMs;
	}

	public void setOutboxIntervalMs(int outboxIntervalMs) {
		this.outboxIntervalMs = outboxIntervalMs;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}
}
