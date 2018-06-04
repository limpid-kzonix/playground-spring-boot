package com.omniesoft.commerce.common.ws;

import org.springframework.web.client.RestTemplate;


public abstract class RestTemplateAbstraction {

    protected String baseUrl;
    protected RestTemplate restTemplate;

    public RestTemplateAbstraction(String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }
}
