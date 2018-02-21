package com.omniesoft.commerce.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;

@Configuration
public class SocialConfig {

    @Bean
    public FacebookConnectionFactory facebookConnectionFactory(@Value("${spring.social.facebook.appId}") String appId,
                                                               @Value("${spring.social.facebook.appSecret}") String
                                                                       appSecret) {

        return new FacebookConnectionFactory(appId, appSecret);
    }

    @Bean
    public GoogleConnectionFactory googleConnectionFactory(@Value("${spring.social.google.appId}") String appId,
                                                           @Value("${spring.social.google.appSecret}") String
                                                                   appSecret) {
        return new GoogleConnectionFactory(appId, appSecret);
    }
}
