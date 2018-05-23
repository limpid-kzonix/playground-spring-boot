package com.omniesoft.commerce.common.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Slf4j
public abstract class SecuredRestTemplateAbstraction extends RestTemplateAbstraction {

    private TokenRestTemplate tokenRestTemplate;

    public SecuredRestTemplateAbstraction(String baseUrl, RestTemplate restTemplate, TokenRestTemplate tokenRestTemplate) {

        super(baseUrl, restTemplate);
        this.tokenRestTemplate = tokenRestTemplate;
    }

    protected void send(URI uri, HttpMethod method, Object payload) {

        HttpEntity httpEntity = getHttpEntity(payload);
        log.info("URI call {} {}  Prepared HTTP entity hasBody {}", method.name(), uri.toString(), httpEntity.hasBody());

        log.info("################################################");
        ResponseEntity<String> s = restTemplate.exchange(uri, method, httpEntity, String.class);
        log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^{}", s.getStatusCode());
        log.info("RESPONCE: {}", s.getBody());

    }


    /**
     * @param payload Users data for logging
     */

    private HttpEntity getHttpEntity(Object payload) {
        return new HttpEntity<>(payload, getTokenForRequest());
    }

    private HttpHeaders getTokenForRequest() {
        HttpHeaders headers = new HttpHeaders();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        OAuth2Authentication oauth = (OAuth2Authentication) securityContext.getAuthentication();
        if (oauth.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oauth.getDetails();
            headers.set(HttpHeaders.AUTHORIZATION, details.getTokenType() + " " + details.getTokenValue());
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        } else {
            OAuth2AccessToken exchange = tokenRestTemplate.exchange();
            headers.set(HttpHeaders.AUTHORIZATION, exchange.getTokenType() + " " + exchange.toString());
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }
        return headers;
    }

    protected CompletableFuture<Void> wrap(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

}
