package dev.ohhoonim.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import dev.ohhoonim.component.signJwt.BearerAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    SecurityFilterChain securityFitlerChain(HttpSecurity http,
            BearerAuthenticationFilter bearerFilter) throws Exception {
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
                .addFilterAfter(bearerFilter, LogoutFilter.class);

        return http.build();
    }
}
