package dev.ohhoonim.user.model;

import java.util.List;
import org.springframework.stereotype.Service;
import dev.ohhoonim.menu.SensitivityLevelCode;
import dev.ohhoonim.user.application.UserAttributeActivity;
import dev.ohhoonim.user.port.UserPipPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class UserPipService extends User implements UserAttributeActivity {

    private final UserPipPort userPipPort;

    @Override
    public List<String> getPermissions() {
        return userPipPort.getPermissions();
    }

    @Override
    public SensitivityLevelCode getSensitivity() {
        return userPipPort.getSensitivity();
    }
}
