package com.omniesoft.commerce.security.config;

import com.google.common.collect.Lists;
import com.omniesoft.commerce.security.jwt.CustomEnhancer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author Vitalii Martynovskyi
 * @since 19.09.17
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private ClientDetailsService clientDetailsService;

    private UserDetailsService userDetailsService;


    private CustomEnhancer customEnhancer;
    @Value("${jwt.keystore.storepass}")
    private String password;
    @Value("${jwt.keystore.name}")
    private String keyStore;
    @Value("${jwt.keystore.alias}")
    private String keyAlias;

    public AuthorizationServerConfiguration(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                            ClientDetailsService clientDetailsService,
                                            UserDetailsService userDetailsService,
                                            CustomEnhancer customEnhancer) {

        this.authenticationManager = authenticationManager;
        this.clientDetailsService = clientDetailsService;
        this.userDetailsService = userDetailsService;

        this.customEnhancer = customEnhancer;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("web-client")
                .secret("secret")
                .scopes("read", "write", "trust")
                .autoApprove(true)
                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                .accessTokenValiditySeconds(60 * 60 * 24 * 7)

                .and()

                .withClient("owner-module")
                .secret("24QoO4YAf7iERNlW")
                .scopes()
                .scopes("read", "write", "trust")
                .authorizedGrantTypes("client_credentials", "password", "implicit", "refresh_token")
                .accessTokenValiditySeconds(60 * 60 * 24 * 7)

                .and()

                .withClient("statistic-module")
                .secret("24QoO4YAf7iERNlW")
                .scopes()
                .scopes("read", "write", "trust")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                .accessTokenValiditySeconds(60 * 60 * 24 * 7)
        ;

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .tokenServices(tokenServices())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
        endpoints.setClientDetailsService(clientDetailsService);
    }

    @Bean
    public TokenStore tokenStore() {

        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(keyStore),
                password.toCharArray()
        );
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyAlias));
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {

        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }


    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {

        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Lists.newArrayList(customEnhancer, jwtTokenEnhancer()));
        return tokenEnhancerChain;
    }


}

