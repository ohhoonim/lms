package dev.ohhoonim.component.signJwt;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/signJwt")
public class SignController {

    private final SignUsecase signService;

    public SignController(SignUsecase signService) {
        this.signService = signService;
    }

    @PostMapping("/login")
    public SignVo login(@RequestParam("username") String username, 
            @RequestParam("password") String password) {
        var loginUser = new User(username, password);
        return signService.signIn(loginUser);
    }

    @PostMapping("logout")
    public void logout() {

    }

    @GetMapping("/refresh") 
    public SignVo refresh(HttpServletRequest request) {
        var bearerToken = request.getHeader("Authorization");
        if (!(StringUtils.hasText(bearerToken) && 
                bearerToken.startsWith("Bearer ")) ) {
            throw new RuntimeException("토큰이 존재하지 않습니다");
        }
        return signService.refresh(bearerToken.substring(7));
    }

    @GetMapping("/forFilterTest")
    public String forTest() {
        return "success";
    }

    @GetMapping("/main")
    public String signOutMain() {
        return "main";
    }
}
