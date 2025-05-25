package dev.ohhoonim.component.signJwt;

import java.util.Optional;

public interface UserPort {
    void addUser(User newUser);

    Optional<User> findByUsernamePassword(String name, String password);
}