package com.omniesoft.commerce.common.rest;

import com.omniesoft.commerce.common.notification.NotifMessage;
import com.omniesoft.commerce.common.notification.order.payload.OrderNotifPl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@AllArgsConstructor
@Service
public class TokenRT implements ITokenRT {
    private final RestTemplate rt;

    @Override
    public void sentHttpRequest(NotifMessage<OrderNotifPl> message, URI uri) {

        HttpEntity httpEntity = createHttpEntity(message);

        log.info("User Notif:  {} entity hasBody {}", uri.toString(), httpEntity.hasBody());
        ResponseEntity<String> resp = rt.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            log.error("Notification Request fail");
        }
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
