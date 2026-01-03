package dev.ohhoonim.user.application;

import java.util.List;
import dev.ohhoonim.menu.SensitivityLevelCode;

public interface UserAttributeActivity {

    List<String> getPermissions();

    SensitivityLevelCode getSensitivity();
    
}
