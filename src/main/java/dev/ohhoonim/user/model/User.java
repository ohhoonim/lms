package dev.ohhoonim.user.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public sealed class User implements Entity permits UserBatchService, UserPipService, UserService {

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

/*
 

```plantuml
@startuml

entity User <<AggregateRoot>>{
  userId: Id
  username: string {required, unique}
  password: string
  name: string
  email: string
  contact: string
  accountStatus: AccountStatus
}

entity AccountStatus {
  signUserId: User 
  enabled: boolean
  locked: boolean
  failedAttemptCount: number
}

entity ChangeDetail{
  changeDetailId: Id
  attributeName: string
  oldValue: SignUserAttribute
  newValue: SignUserAttribute
}

entity PendingChange {
  pendingChangeId: Id
  userId: User 
  changeType: string
  effectiveDate: datatime
  status: string
}

entity UserAttribute {
  attributeId: Id
  name: string
  value: string
}

User [username] -left-  AccountStatus
User "1" --> "attributes[0..*]" UserAttribute 
User "1" -- "pendingChanges[0..*] " PendingChange

PendingChange "1" --> "changeDetails [1..*]" ChangeDetail

@enduml
```
 */
