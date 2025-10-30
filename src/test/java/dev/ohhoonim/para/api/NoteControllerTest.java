package dev.ohhoonim.para.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Tag;
import dev.ohhoonim.para.activity.service.NoteService;
import dev.ohhoonim.para.activity.service.TagService;
import dev.ohhoonim.para.api.NoteController.NoteRequest;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    NoteService noteService;

    @MockitoBean
    TagService tagService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void findNote() throws JsonProcessingException {

        // SearchContainerTest.java 로 이동

        // var noteReq = new NoteRequest("", new Page());
        // var reqData = objectMapper.writeValueAsString(noteReq);
        // mockMvcTester.post()
        //         .uri("/note/list")
        //         .contentType(MediaType.APPLICATION_JSON)
        //         .content(reqData) // post method는 여기에 담아서 넘긴다
        //         .assertThat()
        //         .apply(print()) 
        //         .hasStatusOk()
        //         .bodyJson()
        //         .extractingPath("$.code") // JsonPath로 동작 시작
        //         .isEqualTo("SUCCESS");
    }

    @Test
    public void makeUuid() {
        System.out.println("=================================" + new Id().toString());
    }

    @Test
    @WithMockUser
    public void getNote() {
        mockMvcTester.get()
                .uri("/note/524NN3ZV6J9HBR804FT3JZV8P6")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.message")
                .isEqualTo("note가 존재하지 않습니다");
    }

    @Test
    @WithMockUser
    public void addNote() throws JsonProcessingException {
        var noteId = new Id();
        var stubNote = new Note(noteId, "economic", "micoro");
        when(noteService.addNote(any())).thenReturn(Optional.of(stubNote));

        var note = new Note(null, "economic", "micro");
        var newNoteString = objectMapper.writeValueAsString(note);
        mockMvcTester.post().with(csrf())
                .uri("/note/addNote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNoteString)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.noteId")
                .isEqualTo(noteId.toString());
    }

    @Test
    @WithMockUser
    public void modifyNote() throws JsonProcessingException {
        var noteId = new Id();
        var stubNote = new Note(noteId, "economic", "micoro");
        when(noteService.modifyNote(any())).thenReturn(Optional.of(stubNote));

        var note = new Note(null, "economic", "micro");
        var newNoteString = objectMapper.writeValueAsString(note);
        mockMvcTester.post().with(csrf())
                .uri("/note/modifyNote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNoteString)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.noteId")
                .isEqualTo(noteId.toString());

    }

    @Test
    @WithMockUser
    public void removeNote() throws JsonProcessingException {
        var noteId = new Id();
        mockMvcTester.post().with(csrf())
                .uri("/note/" + noteId.toString() + "/removeNote")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

        verify(noteService, times(1)).removeNote(any());
    }

    @Test
    @WithMockUser
    public void tags() {
        var noteId = new Id();
        mockMvcTester.get()
                .uri("/note/" + noteId + "/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    @WithMockUser
    public void registTag() throws JsonProcessingException {
        var newTag = new Tag(null, "java");
        var noteId = new Id();
        mockMvcTester.post().with(csrf())
                .uri("/note/" + noteId + "/registTag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTag))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    @WithMockUser
    public void removeTag() throws JsonProcessingException {
        var targetTag = new Tag(new Id(), "java");
        var noteId = new Id();
        mockMvcTester.post().with(csrf())
                .uri("/note/" + noteId + "/removeTag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(targetTag))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    @WithMockUser
    public void paras() {
        var noteId = new Id();
        mockMvcTester.get()
                .uri("/note/" + noteId + "/paras")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    @WithMockUser
    public void registPara() throws JsonProcessingException {

        var para = new ParaReq(new Id().toString(), "project");
        var noteId = new Id().toString();

        var json = objectMapper.writeValueAsString(para);
        String category = JsonPath.read(json, "$.category"); 
        assertThat(category).isEqualTo("project");

        mockMvcTester.post().with(csrf())
                .uri("/note/" + noteId + "/registPara")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(para))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    @WithMockUser
    public void removePara() throws JsonProcessingException {
        var para = new ParaReq(new Id().toString(), "project");
        var noteId = new Id();
        mockMvcTester.post().with(csrf())
                .uri("/note/" + noteId + "/removePara")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(para))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    @WithMockUser
    public void findTagsLimit20PerPageTest() throws JsonProcessingException {
        var noteReq = new NoteRequest(
                "");
        mockMvcTester.post().with(csrf())
                .uri("/note/searchTags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteReq))
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.code")
                .isEqualTo("ERROR");
        // SearchContainerTest 참고
    }

}
