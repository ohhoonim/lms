package dev.ohhoonim.component.sign.activity;

import java.util.Date;
import java.util.List;

import dev.ohhoonim.component.sign.Authority;

public interface BearerTokenActivity {

    String generateAccessToken(String userName, List<Authority> authorities);

    String getUsername(String refreshToken);

    Date getExpired(String refreshToken);

    String generateRefreshToken(String userName, List<Authority> authorities);

    // for test
    String generateDenyToken(String userName, List<Authority> authorities);
}

