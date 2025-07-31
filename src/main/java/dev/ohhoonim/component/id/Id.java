package dev.ohhoonim.component.id;

import java.io.Serializable;
import java.util.UUID;

import com.github.f4b6a3.ulid.Ulid;

public class Id implements Serializable {

    private UUID id;

    public Id() {
        this.id = UUID.randomUUID();
    }

    private Id(String ulidType) {
        if (ulidType == null || ulidType.length() != 26) {
            throw new RuntimeException("id가 존재하지 않거나 ulid 형식이 아닙니다");
        }
        this.id = Ulid.from(ulidType).toUuid();
    }

    @Override
    public String toString() {
        return Ulid.from(id).toString();
    }

    public static Id valueOf(String ulid) {
        return new Id(ulid);
    }

    public String getId() {
        return this.toString();
    }
}
