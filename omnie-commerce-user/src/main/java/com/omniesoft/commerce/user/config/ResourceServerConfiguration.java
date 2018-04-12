package com.omniesoft.commerce.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author Vitalii Martynovskyi
 * @since 18.09.17
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

//    private RemoteTokenServices remoteTokenServices;


//    public ResourceServerConfiguration(@Qualifier("remoteTokenService") RemoteTokenServices remoteTokenServices) {
//
//        this.remoteTokenServices = remoteTokenServices;
//    }


//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenServices(remoteTokenServices);
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/account/profile**").authenticated()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
                        "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-ui.html",
                        "/swagger-resources/configuration/security",
                        "/account/signup**",
                        "/account/confirmation/**",
                        "/account/email/change/**",
                        "/account/password/forgot/**"
                ).permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }


}
