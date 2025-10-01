package dev.ohhoonim.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.ohhoonim.component.auditing.dataBy.DataBy;
import dev.ohhoonim.component.auditing.dataBy.Entity;
import dev.ohhoonim.component.auditing.dataBy.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Entity {

    private Id userId;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String email;
    private String contact;
    private AccountStatus accountStatus;
    private DataBy creator;
    private DataBy modifier;

    private List<UserAttribute> attributes;
    private List<PendingChange> pendingChanges;

    public User(Id newId) {
        this.userId = newId;
    }

    public User(String username) {
        setUsername(username);
    }

    @Override
    public Id getId() {
        return this.userId;
    }
}
