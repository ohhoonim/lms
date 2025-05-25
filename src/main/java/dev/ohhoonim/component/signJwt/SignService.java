package dev.ohhoonim.component.signJwt;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("signService")
public class SignService implements SignUsecase {

    private final UserPort userPort;
    private final BearerTokenUsecase bearerTokenService;
    private final AuthorityPort authorityPort;

    public SignService(UserPort userPort,
            AuthorityPort authorityPort,
            BearerTokenUsecase bearerTokenService) {
        this.userPort = userPort;
        this.authorityPort = authorityPort;
        this.bearerTokenService = bearerTokenService;
    }

    @Override
    public void signUp(User newUser) {
        userPort.addUser(newUser);
    }

    @Override
    public SignVo signIn(User loginTryUser) {
        var user = userPort.findByUsernamePassword(loginTryUser.name(), loginTryUser.password());
        if (user.isPresent()) {
            return generateTokenVo(user.get().name());
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }

    @Override
    public SignVo refresh(String refreshToken) {
        try {
            String userName = bearerTokenService.getUsername(refreshToken);
            return generateTokenVo(userName);
        } catch (Exception e) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }
    }

    private SignVo generateTokenVo(String userName) {
        List<Authority> authorities = authorityPort.authoritiesByUsername(userName);
        String access = bearerTokenService.generateAccessToken(userName, authorities);
        String refresh = bearerTokenService.generateRefreshToken(userName, authorities);
        return new SignVo(access, refresh);
    }

}