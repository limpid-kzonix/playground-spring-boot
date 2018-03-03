package com.omniesoft.commerce.support.config;

import com.omniesoft.commerce.common.handler.exception.handler.RestResponseEntityExceptionHandler;
import com.omniesoft.commerce.common.ws.TokenRestTemplate;
import com.omniesoft.commerce.common.ws.TokenRestTemplateImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
public class BeanDefinitionConfig {

//    private final TokenRestTemplate tokenRestTemplate;

//    private final ModelMapper modelMapper;

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public ResponseEntityExceptionHandler entityExceptionHandler() {
        return new RestResponseEntityExceptionHandler();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TokenRestTemplate tokenRestTemplate(
            final @Value("${communication.services.token-service.api.baseUrl}") String baseUrl,
            final @Value("${security.oauth2.client.clientId}") String clientId,
            final @Value("${security.oauth2.client.clientSecret}") String secret
    ) {

        return new TokenRestTemplateImpl(baseUrl, restTemplate(), clientId, secret);
    }

//    @Bean
//    public SubServicePriceConverter subServicePriceConverter() {
//        return new SubServicePriceConverterImpl();
//    }
//
//    @Bean
//    public ServicePriceConverter servicePriceConverter() {
//        return new ServicePriceConverterImpl(modelMapper);
//    }


//    @Bean
//    public OrganizationTimeSheetConverter organizationTimeSheetConverter() {
//        return new OrganizationTimeSheetConverterImpl();
//    }

//    @Bean("adminServiceNotificationRestTemplate")
//    public NotificationRestTemplate adminServiceNotificationRestTemplate(
//            final @Value("${communication.services.notification-service.api.baseUrl}") String baseUrl) {
//
//        return new AdminServiceNotificationRestTemplateImpl(baseUrl, restTemplate(), tokenRestTemplate);
//    }
//
//    @Bean
//    public AdminStatisticRestTemplate adminStatisticRestTemplate(
//            final @Value("${communication.services.statistic-service.api.baseUrl}") String baseUrl
//    ) {
//
//        return new AdminStatisticRestTemplateImpl(baseUrl, restTemplate(), tokenRestTemplate);
//    }


//    @Bean
//    public ImageService imageService(
//            final @Value("${communication.services.image-service.api.baseUrl}") String baseUrl
//    ) {
//
//        return new ImageServiceImpl(baseUrl, restTemplate(), tokenRestTemplate);
//    }


    @Bean(name = "httpThreadPoolExecutor")
    public Executor httpThreadPoolExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("RestTemplateServiceLookup-");
        executor.initialize();
        return executor;
    }
}