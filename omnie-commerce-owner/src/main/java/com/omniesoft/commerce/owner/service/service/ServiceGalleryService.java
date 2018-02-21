package com.omniesoft.commerce.owner.service.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.service.ServiceGallerySummary;

import java.util.List;
import java.util.UUID;

public interface ServiceGalleryService {

    UUID setImageToServiceGallery(UUID serviceId, UUID orgId, String image, UserEntity userEntity);

    void deleteImageFromServiceGallery(UUID serviceId, UUID orgId, UUID imageId,
                                       UserEntity userEntity);

    List<ServiceGallerySummary> getServiceGallery(UUID serviceId, UUID orgId, UserEntity userEntity);
}
