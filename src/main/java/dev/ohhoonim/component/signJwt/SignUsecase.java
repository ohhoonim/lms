package dev.ohhoonim.component.signJwt;

public interface SignUsecase {
    void signUp(User newUser);

    SignVo refresh(String refreshToken);

    SignVo signIn(User loginTryUser);
}
