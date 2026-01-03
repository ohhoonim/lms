package dev.ohhoonim.component.sign.api;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.component.sign.application.SignActivity;
import dev.ohhoonim.component.sign.model.SignUser;
import dev.ohhoonim.component.sign.model.SignedToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
class SignController {

    private final SignActivity signService;

    @PostMapping("/in")
    Vo<SignedToken> login(@RequestBody @Valid Search<LoginReq> login) {
        var req = login.getReq();
        var loginUser = new SignUser(req.username(), req.password());
        return signService.signIn(loginUser);
    }

    record LoginReq(
            @NotBlank String username,
            @NotBlank String password) {
    }

    @PostMapping("/out")
    void logout() {

    }

    @GetMapping("/refresh")
    Vo<SignedToken> refresh(HttpServletRequest request) {
        var bearerToken = request.getHeader("Authorization");
        if (!(StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith("Bearer "))) {
            throw new RuntimeException("토큰이 존재하지 않습니다");
        }
        return signService.refresh(bearerToken.substring(7));
    }

    @GetMapping("/callback/{uri}")
    String signOutMain(@PathVariable("uri") String uri) {
        return uri;
    }
}
