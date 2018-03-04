package com.omniesoft.commerce.common.ws;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;


@Slf4j
@Service
public class TokenRestTemplateImpl extends RestTemplateAbstraction implements TokenRestTemplate {

    private String clientId;

    private String secret;


    public TokenRestTemplateImpl(String baseUrl, RestTemplate restTemplate, String clientId, String secret) {

        super(baseUrl, restTemplate);
        this.clientId = clientId;
        this.secret = secret;


    }

    @Override
    public OAuth2AccessToken exchange() {

        HttpHeaders httpHeaders = createHeaders();

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("grant_type", "client_credentials").build().toUri();
        log.info("Inter-service secured call {}" + uri.toString());
        return restTemplate
                .exchange(uri, HttpMethod.POST, new HttpEntity<>(httpHeaders),
                        OAuth2AccessToken
                                .class).getBody();
    }

    private HttpHeaders createHeaders() {

        String auth = clientId + ":" + secret;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authHeader);
        return httpHeaders;
    }
}
