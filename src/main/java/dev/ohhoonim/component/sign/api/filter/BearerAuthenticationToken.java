package dev.ohhoonim.component.sign.api.filter;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class BearerAuthenticationToken extends AbstractAuthenticationToken{

    private final Object principal;
    private final String credentials;

    public BearerAuthenticationToken(String credentials) {
        // 인증 전에는 권한이 없으므로 null을 전달
        super(null);
        this.principal = null;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public BearerAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return this. principal;
    }


    @Override
    public Object getCredentials() {
        return credentials;
    }
    
}
