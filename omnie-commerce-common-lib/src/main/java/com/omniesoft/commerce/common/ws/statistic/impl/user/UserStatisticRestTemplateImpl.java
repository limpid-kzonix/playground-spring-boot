package com.omniesoft.commerce.common.ws.statistic.impl.user;

import com.omniesoft.commerce.common.ws.SecuredRestTemplateAbstraction;
import com.omniesoft.commerce.common.ws.TokenRestTemplate;
import com.omniesoft.commerce.common.ws.statistic.impl.UserStatisticRestTemplate;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.OrgLogPayload;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.UserLogPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j

@Service
public class UserStatisticRestTemplateImpl extends SecuredRestTemplateAbstraction implements
        UserStatisticRestTemplate {

    /**
     * @param baseUrl      String ::: main url of application (host + port)
     * @param restTemplate RestTemplate bean
     */


    @Autowired
    public UserStatisticRestTemplateImpl(String baseUrl, RestTemplate restTemplate,
                                         TokenRestTemplate tokenRestTemplate) {

        super(baseUrl, restTemplate, tokenRestTemplate);
    }

    /**
     * @param userId whose users uuid you want to save
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserProfileChanges(UUID userId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/profile").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareUserPayload(userId)));
    }


    /**
     * @param userId whose users uuid you want to save
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserSettingsChanges(UUID userId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/settings").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareUserPayload(userId)));
    }

    private UserLogPayload prepareUserPayload(UUID userId) {

        return new UserLogPayload(userId, LocalDateTime.now());
    }

    /**
     * @param userId whose users uuid you want to save
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserPasswordChanges(UUID userId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/password").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareUserPayload(userId)));

    }

    /**
     * @param userId whose users uuid you want to save
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserOmnieCard(UUID userId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/cards/omnie").build().toUri();

        return wrap(() -> send(uri, HttpMethod.POST, prepareUserPayload(userId)));
    }

    /**
     * @param userId whose users uuid you want to save
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserCardHolder(UUID userId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/cards/holder").build().toUri();

        return wrap(() -> send(uri, HttpMethod.POST, prepareUserPayload(userId)));
    }

    /**
     * @param userId whose users uuid you want to save
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserOrderHistory(UUID userId) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/order/history").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareUserPayload(userId)));
    }

    /**
     * @param userId        whose users uuid for saving to log
     * @param organizations list of uuid`s of organizations for saving to log
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserOrganizationShows(UUID userId, List<UUID> organizations) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/organizations/shows").build().toUri();
        OrgLogPayload orgLogPayload = prepareOrgLogPayload(userId, organizations);
        return wrap(() -> send(uri, HttpMethod.POST, orgLogPayload));
    }

    private OrgLogPayload prepareOrgLogPayload(UUID userId, List<UUID> organizations) {

        return new OrgLogPayload(userId, organizations, LocalDateTime.now());
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserServiceShows(UUID userId, List<UUID> organizations) {

        return null;
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserOrganizationToFavotites(UUID userId, List<UUID> organizations) {

        return null;
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserServiceToFavorites(UUID userId, List<UUID> organizations) {

        return null;
    }

    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserServiceViews(UUID userId, List<UUID> organizations) {

        return null;
    }

    /**
     * @param userId        whose users uuid you want to save
     * @param organizations list of uuid`s of organizations for saving to log
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserOrganizationViews(UUID userId, List<UUID> organizations) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/organizations/views").build().toUri();
        return wrap(() -> send(uri, HttpMethod.POST, prepareOrgLogPayload(userId,
                organizations)));
    }

    /**
     * @param userId  whose users uuid you want to save
     * @param pattern The value that was recorded during the search
     */
    @Async(value = "httpThreadPoolExecutor")
    @Override
    public CompletableFuture<Void> logUserOrganizationSearch(UUID userId, String pattern) {

        if (pattern.length() < 4) return CompletableFuture.completedFuture(null);


        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/users/organizations/search").queryParam("pattern",
                pattern).build().toUri();
        log.info("API call: [Statistic: {}] ", uri.toASCIIString());
        return wrap(() -> send(uri, HttpMethod.POST, prepareUserPayload(userId)));
    }
}
