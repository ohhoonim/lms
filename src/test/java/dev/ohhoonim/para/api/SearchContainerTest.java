package dev.ohhoonim.para.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.para.activity.NoteActivity;
import dev.ohhoonim.para.activity.service.TagService;
import dev.ohhoonim.para.api.NoteController.NoteRequest;

@WebMvcTest(NoteController.class)
public class SearchContainerTest {
    
    @Autowired
    MockMvcTester mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    NoteActivity noteService;

    @MockitoBean
    TagService tagService;

    @Test
    @WithMockUser
    public void searchContainerTest() throws JsonProcessingException {
        NoteRequest req = new NoteRequest("some tiitle"); 
        Page page = new Page(new Id()); // lastSeenKey
        Search<NoteRequest> noteReq = new Search<>(req, page);

        String searchJsonContent = objectMapper.writeValueAsString(noteReq);

        mockMvc.post().uri("/note/list").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(searchJsonContent)
            .assertThat().apply(print())
            .hasStatusOk()
            .bodyJson().extractingPath("$.code")
            .isEqualTo("SUCCESS")
            ;
    }
}
