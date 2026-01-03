package dev.ohhoonim.jsonPlaceholder.port;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class PostProxyConfig {

    @Bean
    PostClient postProxy(RestClient.Builder builder, PostApiProps props) {
        RestClient restClient = builder.baseUrl(props.url()).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(PostClient.class);
    }
}
/*
@startuml
title Declarative HTTP Client Structure

interface PostClient <<@HttpExchange>> {
  + getPosts() : List<Post>
  + getPost(id: int) : Post
}

class PostApiProps <<@ConfigurationProperties>> {
  + url() : String
}

class PostProxyConfig <<@Configuration>> {
  -- Bean Methods --
  + postProxy(...) : PostClient
}

class RestClient <<Spring Core>> {
  + baseUrl(url: String) : Builder
  + build() : RestClient
}

class HttpServiceProxyFactory <<Spring Framework>> {
  + createClient(Class<T> type) : T
}


' 의존성 관계
PostProxyConfig ..> PostClient : (생성 대상)
PostProxyConfig --> RestClient : (사용)
PostProxyConfig --> PostApiProps : (주입/사용)
PostProxyConfig ..> HttpServiceProxyFactory : (사용)

@enduml

 */