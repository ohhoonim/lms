package dev.ohhoonim.component.signJwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class Security {

	private final BearerTokenUsecase  bearerTokenService;
	private final AuthorityPort authorityPort;

	Security(BearerTokenUsecase bearerTokenService,
			AuthorityPort authorityPort) {
		this.bearerTokenService = bearerTokenService;
		this.authorityPort = authorityPort;
	}

	@Bean
	SecurityFilterChain securityFitlerChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> {
					auth
							.requestMatchers("/signJwt/login",
									"/signJwt/logout",
									"/signJwt/main",
									"/signJwt/refresh")
							.permitAll()
							.anyRequest().authenticated();
				})
				.logout(logout -> logout
						.logoutUrl("/signJwt/logout"))
				.sessionManagement(s -> s
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterAfter(new BearerAuthenticationFilter(
					bearerTokenService, authorityPort), LogoutFilter.class);

		return http.build();
	}
}
