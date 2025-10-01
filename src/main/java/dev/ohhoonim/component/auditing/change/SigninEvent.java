package dev.ohhoonim.component.auditing.change;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.sign.SignUser;

public final class SigninEvent implements ChangedEvent<SignUser> {

    private SignUser signUser;
    private Created creator;

    public SigninEvent(SignUser signUser, Created creator) {
        this.signUser = signUser;
        this.creator = creator;
    }

    public String username() {
        return signUser.name();
    }

    public Id getId() {
        return new Id();
    }

    public String getEntityType() {
        return Id.entityType(signUser.getClass()); //sign_user
    }

    public String getEntityId() {
        return null;
    }

    public String getEventType() {
        return EventType.SIGNIN.toString();
    }

    public Created getCreator() {
        return creator;
    }

    public String getJsonData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(signUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json 변환에 실패하였습니다.");
        }
    }
}
