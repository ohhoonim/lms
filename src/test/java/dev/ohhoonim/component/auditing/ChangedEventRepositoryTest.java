package dev.ohhoonim.component.auditing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import dev.ohhoonim.component.auditing.change.ChangedEventListener;
import dev.ohhoonim.component.auditing.change.ChangedEventRepository;
import dev.ohhoonim.component.auditing.change.CreatedEvent;
import dev.ohhoonim.component.auditing.change.LookupEvent;
import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.para.Note;
import tools.jackson.databind.ObjectMapper;

@Testcontainers
@JdbcTest
@Import({ChangedEventRepository.class, ObjectMapper.class, ChangedEventListener.class})
public class ChangedEventRepositoryTest {

    Logger log = LoggerFactory.getLogger(getClass());

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgres =
            new PostgreSQLContainer(DockerImageName.parse("postgres:17.2-alpine"));

    @Autowired
    ChangedEventRepository<Note> changedEventRepository;

    @Test
    public void insertChangedDataThrownTest() {
        var note = new Note(null, "Spring framework");
        var created = new Created("ohhoonim");

        var event = new CreatedEvent<Note>(note, created);

        assertThatThrownBy(() -> changedEventRepository.recordingChangedData(event))
                .hasMessageContaining("entity_id는 필수");
    }

    @Test
    public void insertChangedDataNormalTest() {
        var note = new Note(new Id(), "Spring framework");
        var created = new Created("ohhoonim");

        var event = new CreatedEvent<Note>(note, created);

        changedEventRepository.recordingChangedData(event);
    }

    @Test
    public void historyTest() {
        var noteId = new Id();

        var note = new Note(noteId, "Spring framework");
        var created = new Created("ohhoonim");
        var event = new CreatedEvent<Note>(note, created);
        changedEventRepository.recordingChangedData(event);

        var lookup = new LookupEvent<>(noteId, Note.class);
        List<LookupEvent<Note>> results = changedEventRepository.lookupEvent(lookup);

        log.info("{}", results);

        assertThat(results).hasSize(1);

    }

    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    public void changedEventListenerTest() throws InterruptedException {

        Id noteId = new Id();
        CreatedEvent<Note> event = new CreatedEvent<Note>(new Note(noteId, "this is new note"),
                new Created("ohhoonim"));
        publisher.publishEvent(event);

        var lookup = new LookupEvent<>(noteId, Note.class);
        List<LookupEvent<Note>> results = changedEventRepository.lookupEvent(lookup);

        assertThat(results).hasSize(1);

    }
}
