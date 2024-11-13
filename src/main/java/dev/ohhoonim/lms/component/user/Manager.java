package dev.ohhoonim.lms.component.user;

import java.util.UUID;

public record Manager(
        UUID id,
        String userName,
        String password) implements User {
}
