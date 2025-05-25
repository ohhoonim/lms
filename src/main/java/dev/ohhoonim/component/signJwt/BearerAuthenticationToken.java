package dev.ohhoonim.component.signJwt;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class BearerAuthenticationToken extends AbstractAuthenticationToken{

    public BearerAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }


    @Override
    public Object getPrincipal() {
        // TODO 
        return "matthew";
    }


    @Override
    public Object getCredentials() {
        return null;
    }
    
}
