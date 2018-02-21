package com.omniesoft.commerce.common.ws.imagestorage;

import java.util.concurrent.CompletableFuture;

public interface ImageService {
    CompletableFuture<Void> deleteImage(String objectId);
}
