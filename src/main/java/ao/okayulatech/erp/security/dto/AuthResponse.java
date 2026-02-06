package ao.okayulatech.erp.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
	private String token;
	private String tokenType;
	private long expiresInMinutes;
}
