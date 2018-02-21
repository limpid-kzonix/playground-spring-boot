package com.omniesoft.commerce.owner.service.service.impl;

import com.omniesoft.commerce.common.ws.imagestorage.ImageService;
import com.omniesoft.commerce.owner.service.service.ServiceGalleryService;
import com.omniesoft.commerce.owner.service.service.ServiceScopeCrudService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceGalleryEntity;
import com.omniesoft.commerce.persistence.projection.service.ServiceGallerySummary;
import com.omniesoft.commerce.persistence.repository.service.ServiceGalleryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ServiceGalleryServiceImpl implements ServiceGalleryService {


    private final ServiceGalleryRepository serviceGalleryRepository;

    private final ServiceScopeCrudService serviceScopeCrudService;

    private final ImageService imageService;


    @Override
    @Transactional
    public UUID setImageToServiceGallery(UUID serviceId, UUID orgId, String image, UserEntity userEntity) {

        ServiceEntity serviceEntity = serviceScopeCrudService.find(serviceId);

        ServiceGalleryEntity serviceGalleryEntity = new ServiceGalleryEntity();
        serviceGalleryEntity.setImageId(image);
        serviceGalleryEntity.setService(serviceEntity);
        ServiceGalleryEntity save = serviceGalleryRepository.save(serviceGalleryEntity);
        return save.getId();
    }

    @Override
    @Transactional
    public void deleteImageFromServiceGallery(UUID serviceId, UUID orgId, UUID imageId,
                                              UserEntity
                                                      userEntity) {
        ServiceGalleryEntity one = serviceGalleryRepository.findOne(imageId);
        serviceGalleryRepository.deleteByServiceIdAndId(serviceId, imageId);
        imageService.deleteImage(one.getImageId());
    }

    @Override
    public List<ServiceGallerySummary> getServiceGallery(UUID serviceId, UUID orgId, UserEntity userEntity) {

        return serviceGalleryRepository.findAllByServiceIdAndServiceOrganizationId(serviceId, orgId);
    }
}
