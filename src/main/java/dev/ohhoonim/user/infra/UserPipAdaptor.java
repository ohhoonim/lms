package dev.ohhoonim.user.infra;

import java.util.List;
import org.springframework.stereotype.Component;
import dev.ohhoonim.component.auditing.application.CurrentUser;
import dev.ohhoonim.menu.SensitivityLevelCode;
import dev.ohhoonim.user.port.UserPipPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPipAdaptor implements UserPipPort {

    private final UserPipMapper userPipMapper;

    @Override
    public List<String> getPermissions() {
        return userPipMapper.getPermissions(CurrentUser.getUsername());
    }

    @Override
    public SensitivityLevelCode getSensitivity() {
        return userPipMapper.getSensitivity(CurrentUser.getUsername());
    }
    
}
