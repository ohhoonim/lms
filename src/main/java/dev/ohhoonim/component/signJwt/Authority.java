package dev.ohhoonim.component.signJwt;

import org.springframework.security.core.GrantedAuthority;

public record Authority(
    Long id,
    String authority) implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
