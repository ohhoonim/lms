package dev.ohhoonim.component.sign.port;

import java.util.Optional;

import dev.ohhoonim.component.sign.model.SignUser;

public interface SignUserPort {

    Optional<SignUser> findByUsernamePassword(String name, String password);
}