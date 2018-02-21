package com.omniesoft.commerce.common.ws.notification;

import java.util.concurrent.CompletableFuture;

public interface NotificationRestTemplate<T> {
    CompletableFuture<Void> sendOrderNotification(T notification);

    CompletableFuture<Void> sendConversationNotification(T notification);

    CompletableFuture<Void> sendReviewNotification(T notification);
}
