package dev.ohhoonim.user.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ohhoonim.user.internal.UserBatchService;

@WebMvcTest(UserBatchController.class)
public class UserBatchControllerTest {

    @Autowired
    MockMvcTester mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    UserBatchController userBatchController;

    @MockitoBean
    UserBatchService userBatchService;

    @Test
    @WithMockUser
    void batchUpdate() throws Exception {
        var users = List.of();

        mockMvc.post().with(csrf())
                .uri("/user-batch/batchUpdate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users))
                .assertThat().apply(print())
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void applyPendingChangesToUser() {
        var effectiveDate = "2025-10-01T01:23:43";
        mockMvc.get()
                .uri("/user-batch/applyPendingChangesToUser")
                .contentType(MediaType.APPLICATION_JSON)
                .param("effectiveDate", effectiveDate)
                .assertThat().apply(print())
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void batchRegister() throws Exception {
        var users = List.of();
        mockMvc.post().with(csrf())
                .uri("/user-batch/batchRegister")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users))
                .assertThat().apply(print())
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void translateCsvToUsers() {
        MockMultipartFile csv = new MockMultipartFile(
                "file",
                "users.cs1", // 확장자가 csv 이어야함
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "this is sample csv".getBytes());
        mockMvc.post().with(csrf())
            .uri("/user-batch/translateCsvToUsers")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .multipart().file(csv)
            .assertThat().apply(print())
            .hasStatusOk().bodyJson().extractingPath("$.message")
            .isEqualTo("csv 파일만 업로드해주세요");
    }

    @Test
    @WithMockUser
    void translateExcelToUsers() {
        MockMultipartFile csv = new MockMultipartFile(
                "file",
                "users.xls1", // 확장자가 xls, xlsx 이어야함
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "this is sample excel".getBytes());
        mockMvc.post().with(csrf())
            .uri("/user-batch/translateExcelToUsers")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .multipart().file(csv)
            .assertThat().apply(print())
            .hasStatusOk().bodyJson().extractingPath("$.message")
            .isEqualTo("excel 파일만 업로드해주세요");
    }

    @Test
    @WithMockUser
    void fetchHrSystemToPendingChange() {
        mockMvc.get()
            .uri("/user-batch/fetchHrSystemToPendingChange")
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat().hasStatusOk();

    }

}
