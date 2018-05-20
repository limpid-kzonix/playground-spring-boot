package com.omniesoft.commerce.notification.config.ws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import javax.annotation.Nonnull;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Getter
    private static final String[] TOPICS = {"/queue", "/topic", "/owner", "/user"};

    private final StompChannelInterceptorAdapter stompChannelInterceptorAdapter;

    @Override
    public void registerStompEndpoints(@Nonnull StompEndpointRegistry registry) {

        registry.addEndpoint("/handshake-mobile"); //without support of SockJS : Android
        // TODO: 10.05.18 configurable origins
        registry.addEndpoint("/handshake").setAllowedOrigins("*").withSockJS(); //with support of SockJS : Web
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(TOPICS);
        registry.setApplicationDestinationPrefixes("/ws-api");
        registry.setUserDestinationPrefix("/ws-per");
    }

    ;

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {

        registration.setMessageSizeLimit(128 * 1024);
        registration.setSendTimeLimit(15 * 1000).setSendBufferSizeLimit(512 * 1024);
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        registration.setInterceptors(stompChannelInterceptorAdapter);
        super.configureClientInboundChannel(registration);
    }

}
