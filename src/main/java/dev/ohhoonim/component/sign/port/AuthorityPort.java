package dev.ohhoonim.component.sign.port;

import java.util.List;

import dev.ohhoonim.component.sign.model.Authority;

public interface AuthorityPort {
    List<Authority> authoritiesByUsername(String name);
}