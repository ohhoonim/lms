package dev.ohhoonim.user.model;

import dev.ohhoonim.component.auditing.model.DataBy;
import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAttribute implements Entity {

    private Id attributeId;
    private User user;
    private String name;
    private String value;
    private DataBy creator;
    private DataBy modifier;

    @Override
    public Id getId() {
        return this.attributeId;
    }

    public UserAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
