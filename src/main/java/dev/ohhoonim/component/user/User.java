package dev.ohhoonim.component.user;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public sealed interface User {
    UUID userId();

    String name();

    public record Member(
            UUID userId,
            String name,
            String password,
            String email,
            String phone,
            Instant createdAt)
            implements User {
    }

    public record ClassManager(
            UUID userId,
            String name,
            String email,
            String phone) implements User {
    }

    public record Writer(
            UUID userId,
            String name) implements User {
    }

    public record Professor(
            UUID userId,
            String name,
            String email,
            String phone) implements User {
    }

    public record Assistant(
            UUID userId,
            String name,
            String email,
            String phone) implements User {
    }

    public non-sealed interface Usecase extends User {
        public <T extends User> Optional<T> findByUserId(UUID userId, Class<T> clazz);
    }
}
