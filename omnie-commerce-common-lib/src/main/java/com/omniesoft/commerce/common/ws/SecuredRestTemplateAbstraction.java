package com.omniesoft.commerce.common.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
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
        log.info("URI call {} \n" +
                "Prepared HTTP entity :\n {}", uri.toString(), httpEntity.toString());
        restTemplate.exchange(uri, method, httpEntity, Object.class);
    }


    /**
     * @param payload Users data for logging
     */

    private HttpEntity getHttpEntity(Object payload) {

        return new HttpEntity<>(payload,
                getTokenForRequest());
    }

    private HttpHeaders getTokenForRequest() {

        OAuth2AccessToken exchange = tokenRestTemplate.exchange();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, exchange.getTokenType() + " " + exchange.toString());
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return headers;
    }

    protected CompletableFuture<Void> wrap(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

}
