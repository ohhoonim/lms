package dev.ohhoonim.user.infra;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import dev.ohhoonim.menu.SensitivityLevelCode;

@Mapper
public interface UserPipMapper {
    List<String> getPermissions(@Param("username") String username);

    SensitivityLevelCode getSensitivity(@Param("username") String username);

}
