package com.omniesoft.commerce.common.ws.notification.impl.admin;

import com.omniesoft.commerce.common.ws.SecuredRestTemplateAbstraction;
import com.omniesoft.commerce.common.ws.TokenRestTemplate;
import com.omniesoft.commerce.common.ws.notification.NotificationRestTemplate;
import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * Used by services which work with admin entities (for sending notification for users)
 */
@Service("adminServiceNotificationRestTemplate")
public class AdminServiceNotificationRestTemplateImpl extends SecuredRestTemplateAbstraction implements
        NotificationRestTemplate<NotificationMessage> {

    public AdminServiceNotificationRestTemplateImpl(String baseUrl, RestTemplate restTemplate,
                                                    TokenRestTemplate tokenRestTemplate) {

        super(baseUrl, restTemplate, tokenRestTemplate);
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> sendOrderNotification(NotificationMessage notification) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/receiver/user/order").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, notification));
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> sendReviewNotification(NotificationMessage notification) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/receiver/user/review").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, notification));
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> sendConversationNotification(NotificationMessage notification) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/receiver/user/conversation").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, notification));
    }
}
