package dev.ohhoonim.jsonPlaceholder.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;

import dev.ohhoonim.jsonPlaceholder.port.PostApiProps;

public class PostApiPropsTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(TestConfig.class);

    @Test
    void apiProps() {
        contextRunner
            .withPropertyValues("post-api.url=https://jsonplaceholder.typicode.com")
            .run(ctx -> {
                PostApiProps props = ctx.getBean(PostApiProps.class);
                assertThat(props.url())
                        .isEqualTo("https://jsonplaceholder.typicode.com");
            });
    }

}
/*
 * test code 가 아닌 상황이라면 
 * @ConfigurationPropertiesScan을 사용하는 것을 권장한다 
 * @SpringBootApplication과 함께 사용 
 */
@TestConfiguration
@EnableConfigurationProperties(PostApiProps.class)
class TestConfig {

    @Bean
    String apiUrl(PostApiProps props) {
        return props.url();
    }
}