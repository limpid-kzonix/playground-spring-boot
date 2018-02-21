package com.omniesoft.commerce.common.ws.statistic.impl.admin;

import com.google.common.collect.Lists;
import com.omniesoft.commerce.common.ws.SecuredRestTemplateAbstraction;
import com.omniesoft.commerce.common.ws.TokenRestTemplate;
import com.omniesoft.commerce.common.ws.statistic.impl.AdminStatisticRestTemplate;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.AdminLogPayload;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AdminStatisticRestTemplateImpl extends SecuredRestTemplateAbstraction
        implements AdminStatisticRestTemplate {

    public AdminStatisticRestTemplateImpl(String baseUrl, RestTemplate restTemplate,
                                          TokenRestTemplate tokenRestTemplate) {

        super(baseUrl, restTemplate, tokenRestTemplate);
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logAdminOrganizationChanges(UUID userId, UUID orgId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/organizations").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareAdminLogPayload(userId, null, orgId)));
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logAdminServiceChanges(UUID userId, UUID serviceId, UUID org) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/organizations/services").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareAdminLogPayload(userId, serviceId, org)));
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logAdminNewsChanges(UUID userId, UUID orgId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/organizations/news").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareAdminLogPayload(userId, null, orgId)));

    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logAdminDiscountChanges(UUID userId, UUID orgId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/organizations/discounts").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareAdminLogPayload(userId, null, orgId)));
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logAdminOrderChanges(UUID userId, UUID orgId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/organizations/orders").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareAdminLogPayload(userId, null, orgId)));

    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logAdminClientGroupChanges(UUID userId, UUID orgId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/organizations/clients").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareAdminLogPayload(userId, null, orgId)));
    }

    private AdminLogPayload prepareAdminLogPayload(UUID userId, UUID serviceId, UUID org) {

        return new AdminLogPayload(userId, Lists.newArrayList(serviceId), Lists.newArrayList(org),
                LocalDateTime.now());
    }

}
