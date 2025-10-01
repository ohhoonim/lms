package dev.ohhoonim.user.activity;

import java.time.LocalDateTime;
import java.util.List;

import dev.ohhoonim.user.UserAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {
    private String username;
    private String name;
    private Boolean enabled;
    private Boolean locked;
    private Boolean isInit;
    private LocalDateTime effectiveDate;
    private List<UserAttribute> attributes;

    private String password;
    private String newPassword;
}
