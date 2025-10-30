package dev.ohhoonim.para.api;

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

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Para.ParaEnum;
import dev.ohhoonim.para.Para.Shelf.Area;
import dev.ohhoonim.para.activity.service.ParaService;

@WebMvcTest(ParaController.class)
public class ParaControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    ParaService paraService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void makeUuid() {
        System.out.println(new Id());
    }

    @Test
    @WithMockUser
    public void notes() {
        var paraId = "0AWGCKV2738M6VCT5ZQHAG8N0Y";
        mockMvcTester.get()
                .uri("/para/" + paraId + "/notes")
                .param("category", "project")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    @WithMockUser
    public void registNote() throws JsonProcessingException {
        var paraReq = new ParaNoteReq("project", new Id().toString());
        var body = objectMapper.writeValueAsString(paraReq);
        var paraId = "0AWGCKV2738M6VCT5ZQHAG8N0Y";
        mockMvcTester.post().with(csrf())
                .uri("/para/" + paraId + "/registNote")
                .param("category", "project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    @WithMockUser
    public void removeNote() throws JsonProcessingException {
        var paraReq = new ParaNoteReq("project", new Id().toString());
        var body = objectMapper.writeValueAsString(paraReq);
        var paraId = "0AWGCKV2738M6VCT5ZQHAG8N0Y";
        mockMvcTester.post().with(csrf())
                .uri("/para/" + paraId + "/removeNote")
                .param("category", "area")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    @WithMockUser
    public void moveToPara() throws JsonProcessingException {
        var paraReq = new ParaNoteReq("area", new Id().toString(), "archive");
        var body = objectMapper.writeValueAsString(paraReq);
        var paraId = "0AWGCKV2738M6VCT5ZQHAG8N0Y";

        when(paraService.moveToPara(any(), any()))
                .thenReturn(Optional.of(
                        Para.of(Id.valueOf(paraId), Area.class)));

        mockMvcTester.post().with(csrf())
                .uri("/para/" + paraId + "/moveTo")
                .param("category", "area")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    // @GetMapping("/para/{paraId}")
    @Test
    @WithMockUser
    public void getPara() {
        var paraId = new Id();
        when(paraService.getPara(any()))
                .thenReturn(Optional.of(Para.of(paraId, Area.class)));
        mockMvcTester.get()
                .uri("/para/" + paraId)
                .param("category", "area")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.data.paraId")
                .isEqualTo(paraId.toString());
    }

    @Test
    @WithMockUser
    public void addPara() throws JsonProcessingException {
        var newPara = new ParaReq(null, "area", "economic", "micro ecomonimc");
        when(paraService.addPara(any()))
                .thenReturn(Optional.of(Para.of(new Id(), Area.class)));
        mockMvcTester.post().with(csrf())
                .uri("/para/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPara))
                .assertThat().bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
    }

    @Test
    @WithMockUser
    public void modifyPara() throws JsonProcessingException {
        var paraId = new Id().toString();
        var para = new ParaReq(paraId, "area", "economic", "micro ecomonimc");
        when(paraService.modifyPara(any()))
                .thenReturn(Optional.of(Para.of(new Id(), Area.class)));
        mockMvcTester.post().with(csrf())
                .uri("/para/" + paraId + "/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(para))
                .assertThat().apply(print()).bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    @WithMockUser
    public void removePara() throws JsonProcessingException {
        var paraId = new Id().toString();
        var para = new ParaReq(paraId, null, null, null);
        mockMvcTester.post().with(csrf())
                .uri("/para/" + paraId + "/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(para))
                .assertThat().apply(print()).bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");
        verify(paraService, times(1)).removePara(any());
    }

    @Test
    @WithMockUser
    public void findProject() throws JsonProcessingException {
        var project = new ParaReq(null, ParaEnum.Project.toString(), null, null);
        var search = new Search<ParaReq>(project, new Page());
        mockMvcTester.post().with(csrf())
                .uri("/para/searchProjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(search))
                .assertThat().apply(print()).bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

    @Test
    @WithMockUser
    public void findShelves() throws JsonProcessingException {
        var area = new ParaReq(null, ParaEnum.Area.toString(), null, null);
        var search = new Search<ParaReq>(area, new Page());
        mockMvcTester.post().with(csrf())
                .uri("/para/searchShelves")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(search))
                .assertThat().apply(print()).bodyJson()
                .extractingPath("$.code")
                .isEqualTo("SUCCESS");

    }

}
