package dev.ohhoonim.component.sign;

import java.util.List;

public record SignUser(
        String name,
        String password,
        List<Authority> authorities) {
    public SignUser(String username, String password) {
        this(username, password, null);
    }

    public SignUser(String username) {
        this(username, null, null);
    }
}
