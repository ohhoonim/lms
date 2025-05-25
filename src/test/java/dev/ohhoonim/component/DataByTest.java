package dev.ohhoonim.component;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.ohhoonim.component.dataBy.DataBy;

public class DataByTest {
    
    @Test
    @DisplayName("최초 데이터가 생성되면 생성자와 수정자가 동일하다")
    public void createLogUserTest() {
        var userId = UUID.randomUUID();
        var dataBy = new DataBy.Create(userId, LocalDateTime.now());
        assertThat(dataBy.createdBy()).isEqualTo(dataBy.lastModifiedBy());
        assertThat(dataBy.createdDate())
                .isEqualTo(dataBy.lastModifiedDate());
    }

    @Test
    @DisplayName("수정시에는 생성자의 아이디를 null 로 처리한다")
    public void modifyLogUserTest() {
        var userId = UUID.randomUUID();
        var dataBy = new DataBy.Modify(userId);

        assertThat(dataBy.createdBy()).isNull();
        assertThat(dataBy.lastModifiedBy()).isEqualTo(userId);
    }
}


