package com.omniesoft.commerce.security.config;

import com.omniesoft.commerce.common.handler.exception.handler.RestResponseEntityExceptionHandler;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class BeanDefinitionConfig {

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    @Bean
    public ResponseEntityExceptionHandler entityExceptionHandler() {
        return new RestResponseEntityExceptionHandler();
    }

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper;
    }
}

