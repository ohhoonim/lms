package dev.ohhoonim.component.auditing.infra;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebMvcTest(Testcontroller.class)
public class DataByInstantTest {

    Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Test
    @DisplayName("""
           Instant와 LocalDateTime의 차이 비교   
            """)
    public void instantTest() {
        Instant now = Instant.now();
        LocalDateTime localNow = LocalDateTime.now();

        log.info("{}", now);
        log.info("{}", localNow.atZone(ZoneId.of("Asia/Seoul")).toInstant()); 
        /* 
         * Instant와 LocalDateTime의 차이
         * LocalDateTime은 TimeZone이 적용된 상태이다. 
        */
    }

    @Autowired
    MockMvcTester mockMvc;

    @Test
    @DisplayName("""
           프론트에서 어떻게 보내야 Instant로 받을 수 있을까 
            """)
    @WithMockUser
    public void controllerInstantTest() {
        mockMvc.get().uri("/test/iso")
                .param("now", "2025-08-18T04:56:16.517031Z") // 'Z'가 있다
                .accept(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk();
    }
    
    @Test
    @DisplayName("""
           LocalDateTime으로 받는 방법 
            """)
    @WithMockUser
    public void controllerLocalTimeTest() {
        mockMvc.get().uri("/test/local")
                .param("now", "2025-08-18T04:56:16.517031") // 'Z'가 없다. 
                .accept(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk();

        /*
         * javascript 에서 toISOString() 을 이용하여 iso형식으로 만들면 맨 끝에 'Z'가 
         * 붙는다.  (UTC, 협정세계시) UTC로 보내면 java에서 Instant로 받을 수 있다. 
         * LocalDateTime으로는 'Z'를 뺀 형식만 인식한다.
         * 
         */
    }


    @Test
    @DisplayName("""
           날짜 구간 검색시 생각해볼 문제 
            """)
    public void periodCondition() {
        /*
         * zone을 고려하지 않는다면 그냥 LocalDate or LocalDateTime으로 처리
         * zone을 고려한다면 LocalDate or LocalDateTime에 대응되는 컬럼 외에 ZoneId 컬럼 추가
         * 
         * audit 필드 created_at, modified_at 등의 컬럼은 Instant로 처리
         * Instant는 데이터가 저장되는 시점을 나타내는 곳에서만 사용 
         * 
         * 
         */
    }
}


@RestController
class Testcontroller {

    @GetMapping("/test/iso")
    Audit test(@RequestParam("now") Instant now) {
        return new Audit(now);
    }
    
    @GetMapping("/test/local")
    AuditLocale test(@RequestParam("now") LocalDateTime now) {
        return new AuditLocale(now);
    }
}

record Audit(Instant now) {}
record AuditLocale(LocalDateTime now) {
    public AuditLocale(String paramNow) {
        this(LocalDateTime.parse(paramNow));
    } 
}
