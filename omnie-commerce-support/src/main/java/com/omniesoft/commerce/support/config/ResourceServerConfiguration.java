package com.omniesoft.commerce.support.config;

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

//    @Autowired
//    @Qualifier("remoteTokenServices")
//    private RemoteTokenServices remoteTokenServices;

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenServices(remoteTokenServices);
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**"
                ).permitAll()

                .and()

                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .authorizeRequests().anyRequest()
                .hasRole("OWNER");
    }

//    @Bean("remoteTokenServices")
//    public RemoteTokenServices remoteTokenServices(final @Value("${security.oauth2.client.accessTokenUri}") String checkTokenUrl,
//                                                   final @Value("${security.oauth2.client.clientId}") String clientId,
//                                                   final @Value("${security.oauth2.client.clientSecret}") String clientSecret) {
//        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUrl);
//        remoteTokenServices.setClientId(clientId);
//        remoteTokenServices.setClientSecret(clientSecret);
//        remoteTokenServices.setAccessTokenConverter(jwtTokenEnhancer());
//        return remoteTokenServices;
//    }
//
//
//    @Bean
//    @Primary
//    protected JwtAccessTokenConverter jwtTokenEnhancer() {
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
//                new ClassPathResource("jwt.jks"),
//                "xjS9GwYKV3uxEN23".toCharArray()
//        );
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
//        return converter;
//    }


}
