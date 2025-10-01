package dev.ohhoonim.user.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class SignUserAdaptorTest {

    @InjectMocks
    SignUserAdaptor signUserAdaptor;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;
    
    @Test
    void findByUsernamePassword() {
        var name = "matthew";
        var password = "abcd1234";

        when(userMapper.findByUsernamePassword(any(), any())).thenReturn(null);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");

        var signUser = signUserAdaptor.findByUsernamePassword(name, password);
        assertThat(signUser).isEqualTo(Optional.empty());
    }
}
