package dev.ohhoonim.component.attachFile;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.auditing.dataBy.Modified;

@Testcontainers
@RunWith(SpringRunner.class)
@MybatisTest
public class AttachFileMapperTest {
    Logger log = LoggerFactory.getLogger(AttachFileMapperTest.class);

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:17.2-alpine"));

    @Autowired
    AttachFileMapper mapper;

    @Test
    @DisplayName("첨부파일 CRUD")
    void insertAttachFile() {
        Id id = new Id();
        var attach = AttachFile.builder()
                .id(id)
                .name("감사패")
                .path("/temp/image")
                .capacity(2_000L)
                .extension("png")
                .creator(new Created("matthew"))
                .modifier(new Modified("matthew"))
                .build();

        mapper.insertAttachFile(attach);

        var savedFile = mapper.selectAttachFile(id);
        assertThat(savedFile).isInstanceOf(AttachFile.class);
        assertThat(savedFile.getId().toString()).isEqualTo(id.toString());
        assertThat(savedFile.getExtension().toString()).isEqualTo("png");

        assertThat(savedFile.getCreator()).isInstanceOf(Created.class);
        var creator= (Created)savedFile.getCreator();
        assertThat(creator.getCreated()).isBefore(Instant.now());

        assertThat(savedFile.getModifier()).isInstanceOf(Modified.class);
        var modifier = (Modified)savedFile.getModifier();
        assertThat(modifier.getModified()).isBefore(Instant.now());

        mapper.deleteAttachFile(id);
        var deletedFile = mapper.selectAttachFile(id);
        assertThat(deletedFile).isNull();

    }

    // group을 사용하게 되면 group table에서도 삭제해주어야함. 
    // 다음 테스트 코드 참고

    @Test
    @DisplayName("이게 첨부파일기능에 대한 메인 로직")
    void fiesInEntity() {
        /*
         * entityId 의 entity는 도메인별 entity를 의미한다. 예를들어,
         * '결재'라는 도메인에서 파일 첨부기능을 사용한다면 결재Id가 엔티티의 ID이다
         */
        var entityId = new Id();
        var file1Id = new Id();
        var file2Id = new Id();

        var attachFile1 = AttachFile.builder()
                .id(file1Id)
                .name("감사패")
                .path("/temp/image/10")
                .capacity(2_000L)
                .extension("png")
                .creator(new Created("matthew"))
                .build();

        var attachFile2 = AttachFile.builder()
                .id(file2Id)
                .name("국무총리상")
                .path("/temp/image/10")
                .capacity(3_345L)
                .extension("jpg")
                .creator(new Created("matthew"))
                .build();

        var group1 = AttachFileGroup.builder()
                .id(new Id())
                .entityId(entityId)
                .fileId(attachFile1)
                .creator(new Created("matthew"))
                .build();

        var group2 = AttachFileGroup.builder()
                .id(new Id())
                .entityId(entityId) // entityId별로 그룹핑하는 것이므로 동일하게  
                .fileId(attachFile2) // 어짜피 id만 사용한다
                .build();

        // - setup 코드가 너무 길었지만 이제 시작해본다
        // - entityId(특정 도메인ID)를 기준으로 두 개의 첨부파일이 있다.
        // - 파일 두개 첨부하고 group 테이블에 entityId를 동일하게 하여 첨부파일 두개에 대한
        //   데이터를 추가해준다. 

        mapper.insertAttachFile(attachFile1);
        mapper.insertAttachFile(attachFile2);

        mapper.insertAttachFileGroup(group1);
        mapper.insertAttachFileGroup(group2);

        var attachFilesInEntity = mapper.selectAttachFiles(entityId);
        assertThat(attachFilesInEntity).hasSize(2); 

        // 삭제할 때는 attach_fie과 attach_file_group 양쪽 모두 삭제해줘야한다 
        // (‼️warning‼️) 순서가 중요하다
        // entityId 전체에 대한 테스트코드 작성은 생략 
        mapper.deleteAttachFileGroupByFileId(file1Id);
        mapper.deleteAttachFile(file1Id);

        var attachFilesInEntityAfterDelete = mapper.selectAttachFiles(entityId);
        assertThat(attachFilesInEntityAfterDelete).hasSize(1); 

    }
}
