package dev.ohhoonim.component;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.component.response.Response;
import dev.ohhoonim.component.response.ResponseCode;

@ComponentScan(basePackages = {"dev.ohhoonim.component"})
@WebMvcTest(controllers = DefaultResponseApi.class)
public class ResponseTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    @WithMockUser
    public void normal() {
        mockMvcTester.get().uri("/test/defaultResponse/normal")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatus2xxSuccessful()
                .bodyJson()
                .isStrictlyEqualTo("{\"code\":\"SUCCESS\",\"data\":{\"greeting\":\"hi, there\"}}");
    }

    @Test
    @WithMockUser
    public void exception() {
        mockMvcTester.get().uri("/test/defaultResponse/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatus2xxSuccessful()
                .bodyJson()
                .isStrictlyEqualTo("{\"code\":\"ERROR\",\"message\":\"처리 중 에러가 발생했습니다.\",\"data\":null}");
    }

    @Test
    @WithMockUser
    public void success() {
        mockMvcTester.get().uri("/test/defaultResponse/type/success")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatus2xxSuccessful()
                .bodyJson()
                .isStrictlyEqualTo("{\"code\":\"SUCCESS\",\"data\":{\"name\":\"matthew\",\"age\":23}}");

    }

    @Test
    @WithMockUser
    public void typeFail() {
        mockMvcTester.get().uri("/test/defaultResponse/type/fail")
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
    @WithMockUser
    public void string() {
        mockMvcTester.get().uri("/test/defaultResponse/string")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatus2xxSuccessful()
                .bodyJson()
                .isStrictlyEqualTo("{\"code\":\"SUCCESS\",\"data\":\"this is String\"}");
    }

    @Test
    @WithMockUser
    public void entityString() {
        mockMvcTester.get().uri("/test/defaultResponse/responseEntity/string")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .apply(print())
                .hasStatus2xxSuccessful()
                .bodyJson()
                .isStrictlyEqualTo("{\"code\":\"SUCCESS\",\"data\":\"entity string\"}");
    }

    @Test
    @WithMockUser
    public void entityObject() {
        mockMvcTester.get().uri("/test/defaultResponse/responseEntity/object")
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

@RestController
@RequestMapping("/test/defaultResponse")
class DefaultResponseApi {

    @GetMapping("/normal")
    public Map<String, String> normal() {
        var returnMap = new HashMap<String, String>();
        returnMap.put("greeting", "hi, there");
        return returnMap;
    }

    @GetMapping("/exception")
    public void exception() {
        throw new RuntimeException("처리 중 에러가 발생했습니다.");
    }

    record MockResult(String name, Integer age) {
    }

    @GetMapping("/type/success")
    public Response.Success success() {
        return new Response.Success(
                ResponseCode.SUCCESS,
                new MockResult("matthew", 23));
    }

    @GetMapping("/type/fail")
    public Response.Fail fail() {
        return new Response.Fail(
                ResponseCode.ERROR,
                "thif is fail message",
                new MockResult("matthew", 23));
    }

    @GetMapping("/string")
    public String string() {
        return "this is String";
    }

    @GetMapping("/responseEntity/string")
    public ResponseEntity<String> entityString() {
        return ResponseEntity.ok().body("entity string");
    }

    @GetMapping("/responseEntity/object")
    public ResponseEntity<MockResult> entityObject() {
        return ResponseEntity.ok().body(new MockResult("matthew", 23));
    }
}