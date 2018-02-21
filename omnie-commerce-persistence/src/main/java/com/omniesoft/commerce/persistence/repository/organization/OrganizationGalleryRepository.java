package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.organization.OrganizationGalleryEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationGallerySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 05.10.17
 */

public interface OrganizationGalleryRepository extends CrudRepository<OrganizationGalleryEntity, UUID>, OrganizationGalleryRepositoryCustom {

    List<OrganizationGallerySummary> findAllByOrganizationId(UUID id);

    Page<OrganizationGallerySummary> findAllByOrganizationId(UUID id, Pageable pageable);
}
