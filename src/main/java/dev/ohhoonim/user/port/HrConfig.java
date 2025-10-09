package dev.ohhoonim.user.port;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HrConfig {

    @Bean
    HrClient hrClient(@Value("${hr.baseUrl}") String baseUrl) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();
        
        return factory.createClient(HrClient.class);
    }

}

