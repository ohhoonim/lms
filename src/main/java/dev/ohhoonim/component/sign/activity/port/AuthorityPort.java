package dev.ohhoonim.component.sign.activity.port;

import java.util.List;

import dev.ohhoonim.component.sign.Authority;

public interface AuthorityPort {
    List<Authority> authoritiesByUsername(String name);
}