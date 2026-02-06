package ao.okayulatech.erp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ao.okayulatech.erp.security.dto.AuthRequest;
import ao.okayulatech.erp.security.dto.AuthResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final long expirationMinutes;

	public AuthController(
		AuthenticationManager authenticationManager,
		JwtService jwtService,
		@Value("${app.security.jwt.expiration-minutes}") long expirationMinutes
	) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.expirationMinutes = expirationMinutes;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtService.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(token, "Bearer", expirationMinutes));
	}
}
