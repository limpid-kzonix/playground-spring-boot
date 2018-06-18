package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.dto.organization.OrganizationRowExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 11.08.17
 */
public interface OrganizationRepositoryCustom {
    OrganizationEntity findAllProfileById(UUID organizationId);

    OrganizationEntity findByIdWithSettingPhonesTimesheetGallery(UUID organizationId);

    OrganizationRowExtendDto findWithUserOrganizationInfo(UUID organizationId, UserEntity userEntity);

    void calculateMarks();

    void setFreezeStatus(UUID organizationId, boolean status, String reason);

    void setDeleteStatus(UUID organizationId, boolean status, String reason);

    void updateBackgroundImage(UUID organizationId, String imageId);

    void updateLogoImageId(UUID organizationId, String imageId);

    void updateLocation(UUID organizationId, String placeId, Double longitude, Double latitude);

    void updateDescription(UUID organizationId, String title, String description);

    OrganizationEntity findByRoleId(UUID roleId);

    List<OrganizationEntity> findByOwnerOrAdmin(UserEntity userEntity);

    OrganizationEntity findByDiscountId(UUID discountId);

    OrganizationEntity findByServiceId(UUID serviceId);

    String findOwnerLogin(UUID organizationId);

    UserEntity findOwnerFetchOauth(UUID organizationId);

}
