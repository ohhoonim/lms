package dev.ohhoonim.user.infra;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.ohhoonim.component.sign.Authority;
import dev.ohhoonim.component.sign.activity.port.AuthorityPort;

@Component
public class AuthorityAdaptor implements AuthorityPort {

    private final UserMapper userMapper;

    public AuthorityAdaptor (UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<Authority> authoritiesByUsername(String name) {
        // FIXME 메뉴관리 구현할 때 이 부분 채워주세요. resources
        throw new UnsupportedOperationException("Unimplemented method 'authoritiesByUsername'");
    }

}
