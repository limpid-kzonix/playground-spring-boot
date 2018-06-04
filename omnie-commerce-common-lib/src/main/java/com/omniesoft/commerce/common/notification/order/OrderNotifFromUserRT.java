package com.omniesoft.commerce.common.notification.order;

import com.omniesoft.commerce.common.Constants;
import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.NotifType;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderNotifFromUserRT implements IOrderNotifRT {
    private final RestTemplate rt;
    private final String apiHost;

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void newOrder(OrderEntity order) {
        if (order.getUser() != null) {
            NotifMessage<OrderNotifPl> message = new NotifMessage<>();
            message.setType(NotifType.ORDER_NEW);
            message.setContent(OrderNotifPl.map(order));
            sentHttpRequest(message);
        }
    }

    private void sentHttpRequest(NotifMessage<OrderNotifPl> message) {

        URI uri = URI.create(apiHost + "/receiver/admin/order");
        HttpEntity httpEntity = createHttpEntity(message);

        log.debug("User Notif:  {} entity hasBody {}", uri.toString(), httpEntity.hasBody());
        ResponseEntity<String> resp = rt.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            log.error("Notification Request fail");
        }
    }

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void confirmOrder(OrderEntity order) {

    }

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void changedOrder(OrderEntity order) {

    }

    @Override
    @Async(Constants.Executors.ASYNC_HTTP)
    public void doneOrder(OrderEntity order) {

    }

    private HttpEntity createHttpEntity(Object payload) {
        return new HttpEntity<>(payload, createHeaders());
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        insertSecurityToken(headers);
        return headers;
    }

    private void insertSecurityToken(HttpHeaders headers) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        OAuth2Authentication oauth = (OAuth2Authentication) securityContext.getAuthentication();
        if (oauth.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oauth.getDetails();
            headers.set(HttpHeaders.AUTHORIZATION, details.getTokenType() + " " + details.getTokenValue());
        } else {
            log.error("User authentication failed");
        }
    }
}
