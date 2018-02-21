package com.omniesoft.commerce.notification.config;

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
public class ApplicationWebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    @Getter
    private static final String[] TOPICS = {"/topic", "/queue"};

    private static final String END_POINT = "/broker";

    private final StompChannelInterceptorAdapter stompChannelInterceptorAdapter;

    @Override
    public void registerStompEndpoints(@Nonnull StompEndpointRegistry registry) {

        registry.addEndpoint(END_POINT); //without support of SockJS : Android
        registry.addEndpoint(END_POINT).withSockJS(); //with support of SockJS : Web
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker(TOPICS);
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

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
