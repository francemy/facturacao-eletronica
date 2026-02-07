package ao.okayulatech.erp.facturacao.service.crypto;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class KeyStoreService {
	private static final Logger logger = LoggerFactory.getLogger(KeyStoreService.class);

	private final SignatureProperties properties;
	private final ResourceLoader resourceLoader;
	private volatile PrivateKey cachedPrivateKey;

	public KeyStoreService(SignatureProperties properties, ResourceLoader resourceLoader) {
		this.properties = properties;
		this.resourceLoader = resourceLoader;
	}

	public PrivateKey loadPrivateKey() {
		if (cachedPrivateKey != null) {
			return cachedPrivateKey;
		}
		if (properties.getKeyStorePath() == null || properties.getKeyStorePath().isBlank()) {
			logger.warn("Keystore não configurado. Gerando chave efêmera para assinatura.");
			cachedPrivateKey = generateEphemeralKey();
			return cachedPrivateKey;
		}
		try {
			Resource resource = resourceLoader.getResource(properties.getKeyStorePath());
			try (InputStream inputStream = resource.getInputStream()) {
				KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
				keyStore.load(inputStream, properties.getKeyStorePassword().toCharArray());
				String alias = properties.getKeyAlias();
				PrivateKey key = (PrivateKey) keyStore.getKey(alias, properties.getKeyPassword().toCharArray());
				Certificate certificate = keyStore.getCertificate(alias);
				if (key == null || certificate == null) {
					throw new IllegalStateException("Chave/certificado não encontrado no keystore.");
				}
				cachedPrivateKey = key;
				return key;
			}
		} catch (Exception ex) {
			throw new IllegalStateException("Falha ao carregar chave de assinatura.", ex);
		}
	}

	private PrivateKey generateEphemeralKey() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			KeyPair pair = generator.generateKeyPair();
			return pair.getPrivate();
		} catch (Exception ex) {
			throw new IllegalStateException("Falha ao gerar chave temporária.", ex);
		}
	}
}
