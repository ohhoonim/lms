package dev.ohhoonim.component.sign.activity.port;

import java.util.Optional;

import dev.ohhoonim.component.sign.SignUser;

public interface SignUserPort {

    Optional<SignUser> findByUsernamePassword(String name, String password);
}