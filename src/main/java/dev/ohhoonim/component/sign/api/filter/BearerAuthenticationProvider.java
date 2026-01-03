package dev.ohhoonim.component.sign.api.filter;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import dev.ohhoonim.component.sign.application.BearerTokenActivity;
import dev.ohhoonim.component.sign.port.AuthorityPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BearerAuthenticationProvider implements AuthenticationProvider {
    
    private final BearerTokenActivity bearerTokenService;
    private final AuthorityPort authorityPort;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        try {
            String username = bearerTokenService.getUsername(token);
            Collection<? extends GrantedAuthority> authorities = authorityPort.authoritiesByUsername(username);
            return new BearerAuthenticationToken(username, token, authorities);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid token or user not found.", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
