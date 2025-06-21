package dev.ohhoonim.component.signJwt;

import java.util.Date;
import java.util.List;

public interface BearerTokenUsecase {

    String generateAccessToken(String userName, List<Authority> authorities);

    String getUsername(String refreshToken);

    Date getExpired(String refreshToken);

    String generateRefreshToken(String userName, List<Authority> authorities);

    // for test
    String generateDenyToken(String userName, List<Authority> authorities);
}

