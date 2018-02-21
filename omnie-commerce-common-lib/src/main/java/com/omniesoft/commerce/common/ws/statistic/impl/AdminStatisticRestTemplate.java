package com.omniesoft.commerce.common.ws.statistic.impl;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface AdminStatisticRestTemplate {
    CompletableFuture<Void> logAdminOrganizationChanges(UUID userId, UUID orgId);

    CompletableFuture<Void> logAdminServiceChanges(UUID userId, UUID serviceId, UUID org);

    CompletableFuture<Void> logAdminNewsChanges(UUID userId, UUID orgId);

    CompletableFuture<Void> logAdminDiscountChanges(UUID userId, UUID orgId);

    CompletableFuture<Void> logAdminOrderChanges(UUID userId, UUID orgId);

    CompletableFuture<Void> logAdminClientGroupChanges(UUID userId, UUID orgId);

}
