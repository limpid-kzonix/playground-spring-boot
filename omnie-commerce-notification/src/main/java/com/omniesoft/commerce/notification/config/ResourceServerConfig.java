package com.omniesoft.commerce.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author Vitalii Martynovskyi
 * @since 18.09.17
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//    @Autowired
//    @Qualifier("remoteTokenServices")
//    private RemoteTokenServices remoteTokenServices;

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//
//        resources.tokenServices(remoteTokenServices);
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/handshake/**",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**"
                ).permitAll()

                .and()

                .authorizeRequests()
                .anyRequest().authenticated();
    }

//    @Bean("remoteTokenServices")
//    public RemoteTokenServices remoteTokenServices(
//            final @Value("${security.oauth2.client.accessTokenUri}") String checkTokenUrl,
//            final @Value("${security.oauth2.client.clientId}") String clientId,
//            final @Value("${security.oauth2.client.clientSecret}") String clientSecret) {
//
//        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUrl);
//        remoteTokenServices.setClientId(clientId);
//        remoteTokenServices.setClientSecret(clientSecret);
//        return remoteTokenServices;
//    }


}
