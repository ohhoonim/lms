package dev.ohhoonim.component.auditing;

import dev.ohhoonim.component.auditing.model.Created;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.sign.model.SignUser;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public final class SigninEvent implements ChangedEvent<SignUser> {

    private SignUser signUser;
    private Created creator;

    public SigninEvent(SignUser signUser, Created creator) {
        this.signUser = signUser;
        this.creator = creator;
    }

    public String username() {
        return signUser.getName();
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
        } catch (JacksonException e) {
            throw new RuntimeException("json 변환에 실패하였습니다.");
        }
    }
}
