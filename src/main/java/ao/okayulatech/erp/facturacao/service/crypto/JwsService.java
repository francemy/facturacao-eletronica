package ao.okayulatech.erp.facturacao.service.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwsService {
	private final KeyStoreService keyStoreService;
	private final SignatureProperties properties;

	public JwsService(KeyStoreService keyStoreService, SignatureProperties properties) {
		this.keyStoreService = keyStoreService;
		this.properties = properties;
	}

	public String signPayload(String payload) {
		String hash = hashPayload(payload);
		SignatureAlgorithm algorithm = SignatureAlgorithm.forName(properties.getAlgorithm());
		return Jwts.builder()
			.claim("payloadHash", hash)
			.setIssuedAt(Date.from(Instant.now()))
			.signWith(keyStoreService.loadPrivateKey(), algorithm)
			.compact();
	}

	private String hashPayload(String payload) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
			StringBuilder builder = new StringBuilder();
			for (byte b : bytes) {
				builder.append(String.format("%02x", b));
			}
			return builder.toString();
		} catch (Exception ex) {
			throw new IllegalStateException("Erro ao gerar hash do payload.", ex);
		}
	}
}
