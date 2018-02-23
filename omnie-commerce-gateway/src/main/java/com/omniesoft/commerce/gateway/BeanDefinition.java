package com.omniesoft.commerce.gateway;

import com.omniesoft.commerce.gateway.handler.ZuulProxyErrorHandler;
import com.omniesoft.commerce.gateway.zuul.ResponseHeaderAppender;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Configuration
@AllArgsConstructor
public class BeanDefinition {

    @Bean
    public ResponseHeaderAppender responseHeaderAppender() {
        return new ResponseHeaderAppender();
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public ResponseEntityExceptionHandler entityExceptionHandler() {

        return new ZuulProxyErrorHandler();
    }
}
