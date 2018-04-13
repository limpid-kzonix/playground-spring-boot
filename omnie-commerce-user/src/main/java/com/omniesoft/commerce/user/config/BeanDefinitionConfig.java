package com.omniesoft.commerce.user.config;

import com.omniesoft.commerce.common.component.url.UrlBuilder;
import com.omniesoft.commerce.common.component.url.UrlBuilderImpl;
import com.omniesoft.commerce.common.converter.OrganizationTimeSheetConverter;
import com.omniesoft.commerce.common.converter.ServicePriceConverter;
import com.omniesoft.commerce.common.converter.ServicePriceListConverter;
import com.omniesoft.commerce.common.converter.SubServicePriceConverter;
import com.omniesoft.commerce.common.converter.impl.OrganizationTimeSheetConverterImpl;
import com.omniesoft.commerce.common.converter.impl.ServicePriceConverterImpl;
import com.omniesoft.commerce.common.converter.impl.ServicePriceListConverterImpl;
import com.omniesoft.commerce.common.converter.impl.SubServicePriceConverterImpl;
import com.omniesoft.commerce.common.handler.exception.handler.RestResponseEntityExceptionHandler;
import com.omniesoft.commerce.common.ws.TokenRestTemplate;
import com.omniesoft.commerce.common.ws.TokenRestTemplateImpl;
import com.omniesoft.commerce.common.ws.imagestorage.ImageService;
import com.omniesoft.commerce.common.ws.imagestorage.impl.ImageServiceImpl;
import com.omniesoft.commerce.common.ws.notification.NotificationRestTemplate;
import com.omniesoft.commerce.common.ws.notification.impl.user.UserServiceNotificationRestTemplateImpl;
import com.omniesoft.commerce.common.ws.statistic.impl.UserStatisticRestTemplate;
import com.omniesoft.commerce.common.ws.statistic.impl.user.UserStatisticRestTemplateImpl;
import com.omniesoft.commerce.mail.MailConfiguration;
import com.omniesoft.commerce.mail.message.MailMessageBuilder;
import com.omniesoft.commerce.mail.message.impl.MailContentBuilder;
import com.omniesoft.commerce.mail.service.AccountMailService;
import com.omniesoft.commerce.mail.service.MailService;
import com.omniesoft.commerce.mail.service.impl.AccountMailServiceImpl;
import com.omniesoft.commerce.mail.service.impl.MailServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.Executor;

@Configuration
@Import(MailConfiguration.class)
@ComponentScan({"com.omniesoft.commerce.common.component.order"})
public class BeanDefinitionConfig {

    @Autowired
    private TokenRestTemplate tokenRestTemplate;


    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public ResponseEntityExceptionHandler entityExceptionHandler() {
        return new RestResponseEntityExceptionHandler();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccountMailService accountMailService() {
        return new AccountMailServiceImpl();
    }

    @Bean
    public MailService mailService() {
        return new MailServiceImpl();
    }

    @Bean
    public MailMessageBuilder messageBuilder() {
        return new MailContentBuilder();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ServicePriceConverter servicePriceConverter() {
        return new ServicePriceConverterImpl(modelMapper());
    }

    @Bean
    public SubServicePriceConverter subServicePriceConverter() {
        return new SubServicePriceConverterImpl();
    }

    @Bean
    public ServicePriceListConverter servicePriceListConverter() {
        return new ServicePriceListConverterImpl(servicePriceConverter());
    }


    @Bean
    public OrganizationTimeSheetConverter organizationTimeSheetConverter() {
        return new OrganizationTimeSheetConverterImpl();
    }


    @Bean
    public ImageService imageService(
            final @Value("${communication.services.image-service.api.baseUrl}") String baseUrl
    ) {

        return new ImageServiceImpl(baseUrl, restTemplate(), tokenRestTemplate);
    }

    /*
     * Depended with tokenRestTemplate
     * */
    @Bean
    public UserStatisticRestTemplate userStatisticRestTemplate(
            final @Value("${communication.services.statistic-service.api.baseUrl}") String baseUrl
    ) {

        return new UserStatisticRestTemplateImpl(baseUrl, restTemplate(), tokenRestTemplate);
    }

    @Bean("userServiceNotificationRestTemplate")
    public NotificationRestTemplate userServiceNotificationRestTemplate(
            final @Value("${communication.services.notification-service.api.baseUrl}") String baseUrl) {

        return new UserServiceNotificationRestTemplateImpl(baseUrl, restTemplate(), tokenRestTemplate);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Need for using another RestTemplate services, which needed auth scheme for http flow
     * */
    @Bean
    public TokenRestTemplate tokenRestTemplate(
            final @Value("${communication.services.token-service.api.baseUrl}") String baseUrl,
            final @Value("${security.oauth2.client.clientId}") String clientId,
            final @Value("${security.oauth2.client.clientSecret}") String secret
    ) {

        return new TokenRestTemplateImpl(baseUrl, restTemplate(), clientId, secret);
    }


    @Bean(name = "httpThreadPoolExecutor")
    public Executor httpThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(1500);
        executor.setThreadNamePrefix("RestTemplateServiceLookup-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "scheduleThreadPoolExecutor")
    public Executor scheduleThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("ScheduleTemplateServiceLookup-");
        executor.initialize();
        return executor;
    }

    @Bean
    public UrlBuilder urlBuilder(final @Value("${application.host}") String host,
                                 final @Value("${application.port}") int port) {
        return new UrlBuilderImpl(host, port);
    }

    public TokenRestTemplate getTokenRestTemplate() {
        return tokenRestTemplate;
    }
}