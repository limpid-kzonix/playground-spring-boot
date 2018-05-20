package com.omniesoft.commerce.notification.config.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        // TODO: 20.05.18 cleanup config
        messages
                .nullDestMatcher().authenticated()
                .simpDestMatchers(
                        "/handshake/**"
                ).permitAll()
                .simpDestMatchers("/ws-per/**", "/ws-api/**",
                        "/topic/**", "/queue/**"
                ).authenticated()

                .simpSubscribeDestMatchers(
                        "/ws-per/**", "/ws-api/**",
                        "/topic/**", "/queue/**",
                        "/user/**", "/owner/**"
                ).authenticated()
                .simpSubscribeDestMatchers("/owner/**").hasRole("OwNER")
                .simpSubscribeDestMatchers("/user/**").hasRole("USER");
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}
