package dev.ohhoonim.component.sign.application;

import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.component.sign.model.SignUser;
import dev.ohhoonim.component.sign.model.SignedToken;

public interface SignActivity {

    Vo<SignedToken> refresh(String refreshToken);

    Vo<SignedToken> signIn(SignUser loginTryUser);
}
