package dev.ohhoonim.component.container.handler;

import java.lang.reflect.ParameterizedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import dev.ohhoonim.component.container.Response;
import dev.ohhoonim.component.container.Response.Fail;
import dev.ohhoonim.component.container.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@RestControllerAdvice(basePackages = "dev.ohhoonim")
public class DefaultResponseHandler implements ResponseBodyAdvice<Object> {

    @SuppressWarnings("null")
    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> type = returnType.getParameterType();
        if (ResponseEntity.class.isAssignableFrom(type)) {
            try {
                ParameterizedType parameterizedType =
                        (ParameterizedType) returnType.getGenericParameterType();
                type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }
        if (Response.class.isAssignableFrom(type)) {
            return false;
        }
        return true;
    }

    @Autowired
    ObjectMapper objectMapper;

    @SuppressWarnings("null")
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        Response responseSuccess = new Response.Success(ResponseCode.SUCCESS, body);

        if (selectedContentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            return responseSuccess;
        }
        try {
            response.getHeaders().set("Content-Type", "application/json");
            return objectMapper.writeValueAsString(responseSuccess);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    ResponseEntity<Response> defaultErrorHandler(HttpServletRequest request, Exception e)
            throws Exception {
        log.error("{}", e.getMessage());
        return ResponseEntity.ok(new Fail(ResponseCode.ERROR, e.getMessage(), null));
    }

}
