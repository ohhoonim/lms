package dev.ohhoonim.component.signJwt;

import java.util.List;
import java.util.UUID;

public record User(
        UUID id,
        String name,
        String password,
        List<Authority> authorities) {
    public User(String username, String password) {
        this(null, username, password, null);
    }
}
