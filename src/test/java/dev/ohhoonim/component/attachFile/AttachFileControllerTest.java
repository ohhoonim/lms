package dev.ohhoonim.component.attachFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(controllers=AttachFileController.class)
public class AttachFileControllerTest {
    
    @Autowired
    MockMvcTester mockMvc;

    @MockitoBean
    AttachFileService attachFileService;

    @Test
    @WithMockUser
    void uploadFileTest() {
        when(attachFileService.uploadFiles(any())).thenReturn(List.of());

        MockMultipartFile mockFile = new MockMultipartFile(
            "files", 
            "test-file.txt",
            "text/plain",
            "Hello, this is test file.".getBytes()
        );

        mockMvc.post().uri("/api/attachFile/upload")
                .multipart().file(mockFile)
                .with(csrf())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .assertThat().apply(print())
                .hasStatusOk();
    }
}
