package com.omniesoft.commerce.notification.config;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "**/configuration/ui",
                "**/swagger-resources",
                "/configuration/security",
                "/api/user/swagger-ui.html",
                "/webjars/**"
        );
    }


}
