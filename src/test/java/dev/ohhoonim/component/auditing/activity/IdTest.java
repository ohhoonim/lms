package dev.ohhoonim.component.auditing.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.f4b6a3.ulid.UlidCreator;

import dev.ohhoonim.component.auditing.model.Id;

public class IdTest {

    @Test
    @DisplayName("""
            신규 생성된 Id 객체의 id는 서로 다르고,
            문자열로 변환시 26자리인지 체크한다.
             """)
    void idToString() {
        var id = new Id();
        var newId = new Id();

        assertThat(id.toString()).isNotEqualTo(newId.toString());
        assertThat(id.toString()).hasSize(26);
    }

    @Test
    @DisplayName("""
            new Id()로 객체 생성시 id필드값을 만들어내는지 확인한다
             """)
    void generateId() {
        var id = new Id();

        assertThat(id.toString()).hasSize(26);
    }

    @Test
    @DisplayName("""
            ulid 로 Id 객체를 생성할 수 있는지 확인한다
             """)
    void fromUlid() {
        var ulid = UlidCreator.getMonotonicUlid();
        Id id = Id.valueOf(ulid.toString());

        assertThat(id.toString()).isEqualTo(ulid.toString());
    }

    @Test
    @DisplayName("""
            ulid 형식이 아닌 문자열은 exception을 던진다
            (IdTypeHanlder 참고)
             """)
    void ulidTypeId() {
        assertThatThrownBy(() -> Id.valueOf("BBWREQGBWREQGCE"))
                .hasMessage("id가 존재하지 않거나 ulid 형식이 아닙니다");

        assertThatThrownBy(() -> Id.valueOf("BBWREQGBWREQGWREQGBWREQGCE")) // <- 26자리 
                .hasMessageContaining("Invalid ULID");
    }
}
