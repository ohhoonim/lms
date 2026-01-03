package dev.ohhoonim.component.sign.model;

import java.util.List;

import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public sealed class SignUser implements Entity permits SignService {

    private String name;
    private String password;
    private List<Authority> authorities;

    public SignUser(String username, String password) {
        this(username, password, null);
    }

    public SignUser(String username) {
        this(username, null, null);
    }

    @Builder
    public SignUser(String name, String password, List<Authority> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Id getId() {
        return new Id();
    }

}
