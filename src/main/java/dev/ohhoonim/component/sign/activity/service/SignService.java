package dev.ohhoonim.component.sign.activity.service;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.component.sign.Authority;
import dev.ohhoonim.component.sign.SignUser;
import dev.ohhoonim.component.sign.SignedToken;
import dev.ohhoonim.component.sign.activity.BearerTokenActivity;
import dev.ohhoonim.component.sign.activity.SignActivity;
import dev.ohhoonim.component.sign.activity.port.AuthorityPort;
import dev.ohhoonim.component.sign.activity.port.SignUserPort;
import lombok.RequiredArgsConstructor;

@Service("signService")
@RequiredArgsConstructor
class SignService implements SignActivity {

    private final SignUserPort signUserPort;
    private final AuthorityPort authorityPort;
    private final BearerTokenActivity bearerTokenService;

    @Override
    public Vo<SignedToken> signIn(SignUser loginTryUser) {
        SignUser user = signUserPort.findByUsernamePassword(loginTryUser.name(), loginTryUser.password())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return generateTokenVo(user.name());
    }

    @Override
    public Vo<SignedToken> refresh(String refreshToken) {
        try {
            String userName = bearerTokenService.getUsername(refreshToken);
            return generateTokenVo(userName);
        } catch (Exception e) {
            throw new BadCredentialsException("유효하지 않은 토큰입니다.", e);
        }
    }

    private Vo<SignedToken> generateTokenVo(String userName) {
        List<Authority> authorities = authorityPort.authoritiesByUsername(userName);
        String access = bearerTokenService.generateAccessToken(userName, authorities);
        String refresh = bearerTokenService.generateRefreshToken(userName, authorities);
        return new Vo<>(new SignedToken(access, refresh));
    }

}