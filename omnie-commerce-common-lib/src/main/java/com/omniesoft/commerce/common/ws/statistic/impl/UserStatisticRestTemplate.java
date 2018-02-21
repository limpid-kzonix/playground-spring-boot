package com.omniesoft.commerce.common.ws.statistic.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserStatisticRestTemplate {

    CompletableFuture<Void> logUserProfileChanges(UUID userId);

    CompletableFuture<Void> logUserSettingsChanges(UUID userId);

    CompletableFuture<Void> logUserPasswordChanges(UUID userId);

    CompletableFuture<Void> logUserOmnieCard(UUID userId);

    CompletableFuture<Void> logUserCardHolder(UUID userId);

    CompletableFuture<Void> logUserOrderHistory(UUID userId);

    CompletableFuture<Void> logUserOrganizationShows(UUID userId, List<UUID> organizations);

    CompletableFuture<Void> logUserOrganizationViews(UUID userId, List<UUID> organizations);

    CompletableFuture<Void> logUserServiceShows(UUID userId, List<UUID> organizations);

    CompletableFuture<Void> logUserOrganizationToFavotites(UUID userId, List<UUID> organizations);

    CompletableFuture<Void> logUserServiceToFavorites(UUID userId, List<UUID> organizations);

    CompletableFuture<Void> logUserServiceViews(UUID userId, List<UUID> organizations);

    CompletableFuture<Void> logUserOrganizationSearch(UUID userId, String pattern);


}
