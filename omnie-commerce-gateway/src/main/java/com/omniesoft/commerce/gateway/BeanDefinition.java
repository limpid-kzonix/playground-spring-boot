package com.omniesoft.commerce.gateway;

import com.omniesoft.commerce.gateway.handler.ZuulProxyErrorHandler;
import com.omniesoft.commerce.gateway.zuul.ResponseHeaderAppender;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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

    @Bean
//    @Profile("!default")
    public CorsFilter corsFilter() {
        // TODO: 16.03.18 make configurable
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
