package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.service.ServiceGalleryEntity;
import com.omniesoft.commerce.persistence.projection.service.ServiceGallerySummary;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface ServiceGalleryRepository extends PagingAndSortingRepository<ServiceGalleryEntity, UUID> {

    List<ServiceGallerySummary> findAllByServiceIdAndServiceOrganizationId(UUID service, UUID org);

    void deleteByServiceIdAndId(UUID serviceId, UUID id);
}
