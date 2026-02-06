package ao.okayulatech.erp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {

	@Bean
	public UserDetailsService userDetailsService(
		@Value("${spring.security.user.name}") String username,
		@Value("${spring.security.user.password}") String password
	) {
		return new InMemoryUserDetailsManager(User.withUsername(username).password(password).roles("ADMIN").build());
	}
}
