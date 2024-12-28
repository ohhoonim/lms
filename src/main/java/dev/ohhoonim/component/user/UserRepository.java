package dev.ohhoonim.component.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcClient client;

    public UserRepository(JdbcClient client) {
        this.client = client;
    }

    public <T extends User> Optional<T> findByUserId(UUID userId, Class<T> clazz) {
        return null;
    };

}
