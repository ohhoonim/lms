package dev.ohhoonim.component;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import dev.ohhoonim.component.response.DefaultResponseApi;

@ComponentScan(basePackages = { "dev.ohhoonim.component.response",
        "dev.ohhoonim.configuration"})
@WebMvcTest(controllers = { DefaultResponseApi.class })
public class ResponseTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    public void normal() {
        mockMvcTester.get().uri("/defaultResponse/normal")
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .apply(print())
            .hasStatus2xxSuccessful()
            .bodyJson()
            .isStrictlyEqualTo("{\"code\":\"SUCCESS\",\"data\":{\"greeting\":\"hi, there\"}}");
    }

    @Test
    public void exception() {
        mockMvcTester.get().uri("/defaultResponse/exception")
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .apply(print())
            .hasStatus2xxSuccessful()
            .bodyJson()
            .isStrictlyEqualTo("{\"code\":\"ERROR\",\"message\":\"처리 중 에러가 발생했습니다.\",\"data\":null}");
    }

    @Test
    public void success() {
        mockMvcTester.get().uri("/defaultResponse/type/success")
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .apply(print())
            .hasStatus2xxSuccessful()
            .bodyJson()
            .isStrictlyEqualTo("{\"code\":\"SUCCESS\",\"data\":{\"name\":\"matthew\",\"age\":23}}");

    }

    @Test
    public void typeFail() {
        mockMvcTester.get().uri("/defaultResponse/type/fail")
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .apply(print())
            .hasStatus2xxSuccessful()
            .bodyJson()
            .isStrictlyEqualTo("""
                {\"code\":\"ERROR\",
                \"message\":\"thif is fail message\",
                \"data\":{\"name\":\"matthew\",\"age\":23}}
                """);
    }

    @Test
    public void string() {
        mockMvcTester.get().uri("/defaultResponse/string")
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .apply(print())
            .hasStatus2xxSuccessful()
            .bodyJson()
            .isStrictlyEqualTo("{\"code\":\"SUCCESS\",\"data\":\"this is String\"}");
    }
    
    @Test
    public void entityString() {
        mockMvcTester.get().uri("/defaultResponse/responseEntity/string")
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .apply(print())
            .hasStatus2xxSuccessful()
            .bodyJson()
            .isStrictlyEqualTo("{\"code\":\"SUCCESS\",\"data\":\"entity string\"}");
    }

    @Test
    public void entityObject() {
        mockMvcTester.get().uri("/defaultResponse/responseEntity/object")
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .apply(print())
            .hasStatus2xxSuccessful()
            .bodyJson()
            .isStrictlyEqualTo("""
                {\"code\":\"SUCCESS\",
                \"data\":{\"name\":\"matthew\",\"age\":23}}
            """);
    }

}
