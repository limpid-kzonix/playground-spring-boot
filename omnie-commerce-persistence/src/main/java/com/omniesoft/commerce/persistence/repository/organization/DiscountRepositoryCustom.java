package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 24.10.17
 */
public interface DiscountRepositoryCustom {

    void deleteByIdAndOrganizationId(UUID discountId, UUID organizationId);

    DiscountEntity findFullDiscountData(UUID discountId, UUID organizationId);

    DiscountEntity findByIdAndOrganizationIdWithServices(UUID discountId, UUID organizationId);

    DiscountEntity findByIdAndOrganizationIdWithSubServices(UUID discountId, UUID organizationId);

    DiscountEntity findByIdAndOrganizationIdWithUsers(UUID discountId, UUID organizationId);

    List<DiscountEntity> findFullDiscountsDataByOrganizationId(UUID organizationId);

    List<DiscountEntity> findByOrganizationIdForUser(UUID organizationId, UserEntity userEntity);

    List<DiscountEntity> findFullDiscountDataByServiceId(UUID organizationId, UUID serviceId, UserEntity userEntity);

    List<DiscountEntity> findNotPersonalByServiceAndActiveDate(UUID serviceId, LocalDateTime start);

    List<DiscountEntity> findPersonalByUserAndServiceAndActiveDate(UUID userId, UUID serviceId, LocalDateTime start);

    List<DiscountEntity> findNotPersonalBySubServiceAndActiveDate(UUID subServiceId, LocalDateTime start);

    List<DiscountEntity> findPersonalByUserAndSubServiceAndActiveDate(UUID userId, UUID subServiceId, LocalDateTime start);

}
