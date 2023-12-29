package dev.ohhoonim.lms.infra.post;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CommentExternalServiceConfig {
    @Bean
    public CommentRestClientRepository commentRestClientRepositoryProxy(RestClient.Builder builder) {
        var restClient = builder.requestFactory(new JdkClientHttpRequestFactory())
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .build();
        var adaptor = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(adaptor).build();
        return factory.createClient(CommentRestClientRepository.class);
    }
}
