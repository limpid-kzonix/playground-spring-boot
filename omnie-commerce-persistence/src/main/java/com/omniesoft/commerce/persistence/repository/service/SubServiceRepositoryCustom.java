package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 25.10.17
 */
public interface SubServiceRepositoryCustom {
    SubServiceEntity findByIdAndOrganizationId(UUID subServiceId, UUID organizationId);

    SubServiceEntity findByIdAndServiceId(UUID id, UUID serviceId);

    void changeDeleteStatus(UUID subServiceId, Boolean deleteStatus);

    List<SubServiceEntity> findByIdAndOrganizationIdWithPrice(UUID serviceId, UUID organizationId);

    List<SubServiceEntity> findByIdAndOrganizationIdWithPriceAndDeleteStatusIsTrue(UUID serviceId, UUID organizationId);
}
