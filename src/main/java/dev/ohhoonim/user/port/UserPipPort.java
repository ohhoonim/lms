package dev.ohhoonim.user.port;

import java.util.List;
import dev.ohhoonim.menu.SensitivityLevelCode;

public interface UserPipPort {

    List<String> getPermissions();

    SensitivityLevelCode getSensitivity();

}
