package dev.ohhoonim.component.user;

import java.util.UUID;

public record Professor(
        UUID id,
        String userName,
        String password) implements User {
}