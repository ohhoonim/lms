package dev.ohhoonim.component.sign.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import dev.ohhoonim.component.sign.api.filter.BearerAuthenticationFilter;
import dev.ohhoonim.component.sign.api.filter.BearerAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
			BearerAuthenticationProvider bearerAuthenticationProvider) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.authenticationProvider(bearerAuthenticationProvider);

		return authenticationManagerBuilder.build();
	}

	@Bean
	SecurityFilterChain securityFitlerChain(HttpSecurity http, AuthenticationManager authenticationManager)
			throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> {
					auth
							.requestMatchers("/", "/css/**", "/js/**", "/images/**", 
									"/sign/in",  "/sign/refresh", "/error")
							.permitAll()
							.anyRequest().authenticated();
				})
				.logout(logout -> logout
						.logoutUrl("/sign/logout")
						.addLogoutHandler(bearerLogoutHandler()))
				.sessionManagement(s -> s
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterAfter(new BearerAuthenticationFilter(authenticationManager), LogoutFilter.class);

		return http.build();
	}

	@Bean
	public LogoutHandler bearerLogoutHandler() {
		return (request, response, authentication) -> {
			// LogoutHandler는 authentication을 자동으로 제거하기 직전에 실행된다.
			// 여기에 로그아웃 시 필요한 추가 로직을 구현합니다.
			// 예: JWT 토큰 무효화 (블랙리스트에 추가 또는 캐시에서 제거)
			// JWT를 HTTP 헤더에서 가져와서 처리해야 합니다.
			// 예) var token = request.getHeader("Authorization").substring(7);
			//     tokenBlacklistService.addToken(token);

			// JWT의 경우 세션 무효화는 필요하지 않으므로, super.onLogoutSuccess(request, response, authentication)는 호출하지 않아도 됩니다.
		};
	}
}
