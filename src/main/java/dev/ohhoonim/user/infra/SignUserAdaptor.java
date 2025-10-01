package dev.ohhoonim.user.infra;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import dev.ohhoonim.component.auditing.change.SigninEvent;
import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.sign.SignUser;
import dev.ohhoonim.component.sign.activity.port.SignUserPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignUserAdaptor implements SignUserPort {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;

    @Override
    public Optional<SignUser> findByUsernamePassword(String name, String password) {
        String username = userMapper.findByUsernamePassword(
                name, passwordEncoder.encode(password));
        
        publisher.publishEvent(new SigninEvent(new SignUser(username), new Created()));
        
        return !StringUtils.hasText(username)  
                ? Optional.empty() 
                : Optional.of(new SignUser(username));
    }

}
