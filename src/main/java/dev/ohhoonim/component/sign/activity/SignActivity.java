package dev.ohhoonim.component.sign.activity;

import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.component.sign.SignUser;
import dev.ohhoonim.component.sign.SignedToken;

public interface SignActivity {

    Vo<SignedToken> refresh(String refreshToken);

    Vo<SignedToken> signIn(SignUser loginTryUser);
}
