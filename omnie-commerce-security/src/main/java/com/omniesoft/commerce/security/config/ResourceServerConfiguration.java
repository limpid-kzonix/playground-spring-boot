package com.omniesoft.commerce.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author Vitalii Martynovskyi
 * @since 18.09.17
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/configuration/ui",
                        "/swagger-ui.html",
                        "/swagger-resources/configuration/security",
                        "/status",
                        "**/oauth/check_token**",
                        "/social/google/**",
                        "/social/google**",
                        "/social/facebook/**",
                        "/social/facebook**"
                ).permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
        http.headers().cacheControl();
    }


}
