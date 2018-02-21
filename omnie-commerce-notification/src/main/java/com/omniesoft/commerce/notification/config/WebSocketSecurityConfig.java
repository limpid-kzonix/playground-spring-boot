package com.omniesoft.commerce.notification.config;

import com.omniesoft.commerce.persistence.entity.enums.AuthorityName;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;

public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

        messages
                .nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
                .simpDestMatchers("/app/**").hasRole(AuthorityName.ROLE_USER.name())
                .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole(AuthorityName.ROLE_USER.name())
                .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
                .anyMessage().denyAll();

        messages.simpDestMatchers("/user/**").authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {

        return true;
    }
}
