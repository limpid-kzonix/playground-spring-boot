package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 22.08.17
 */
public interface ServiceRepositoryCustom {
    ServiceEntity findWithOrganizationAndSettings(UUID id);

    ServiceRowUserExtendDto findWithUserServiceInfo(UUID id, UUID organizationId, UserEntity userEntity);

    void calculateMarks();

    void setFreezeStatus(UUID serviceId, Boolean status, String reason);

    void setDeleteStatus(UUID serviceId, Boolean status);

    List<ServiceEntity> getOrganizationPriceList(UUID organizationId);


}
