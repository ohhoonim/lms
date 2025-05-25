package dev.ohhoonim.component.signJwt;

import java.util.List;

public interface AuthorityPort {
    List<Authority> authoritiesByUsername(String name);
}