package dev.ohhoonim.component.signJwt;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AuthorityAdaptor implements AuthorityPort{

    @Override
    public List<Authority> authoritiesByUsername(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authoritiesByUsername'");
    }
    
}
