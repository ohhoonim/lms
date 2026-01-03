package dev.ohhoonim.component.auditing.activity;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dev.ohhoonim.component.attachFile.AttachFile;
import dev.ohhoonim.component.auditing.model.Created;
import dev.ohhoonim.component.auditing.model.DataBy;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.auditing.model.Modified;

public class DataByTest {

    // mybatis에서 활용법은 AttachFileMapper.xml 참고

    @Test
    @DisplayName("""
                DataBy는  데이터 조작자, 데이터 등록일에 대한
                sealed interface이다.
            """)
    void sealedDataByTest() {
        DataBy creator = new Created("matthew");
        DataBy modifier = new Modified("ohhoonim");

        assertThat(creator).isInstanceOf(Created.class);
        assertThat(((Created) creator).getCreator()).isEqualTo("matthew");

        assertThat(modifier).isInstanceOf(Modified.class);
        assertThat(((Modified) modifier).getModifier()).isEqualTo("ohhoonim");
    }

    @Test
    @DisplayName("""
            Entity에 association 프로퍼티로 활용한다
             """)
    void associationTest() {
        var attachFile = AttachFile.builder()
                .id(new Id())
                .creator(new Created("matthew"))
                .build();

        assertThat(attachFile.getCreator()).isInstanceOf(Created.class);
    }
}
