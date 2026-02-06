package ao.okayulatech.erp.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String secret;
	private final long expirationMinutes;

	public JwtService(
		@Value("${app.security.jwt.secret}") String secret,
		@Value("${app.security.jwt.expiration-minutes}") long expirationMinutes
	) {
		this.secret = secret;
		this.expirationMinutes = expirationMinutes;
	}

	public String generateToken(UserDetails userDetails) {
		Instant now = Instant.now();
		Instant expiry = now.plus(expirationMinutes, ChronoUnit.MINUTES);

		return Jwts.builder()
			.subject(userDetails.getUsername())
			.issuedAt(Date.from(now))
			.expiration(Date.from(expiry))
			.signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
