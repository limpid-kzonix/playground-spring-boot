package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.OwnerEntity;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.10.17
 */
public interface OwnerRepositoryCustom {
    OwnerEntity findByOrganizationId(UUID organizationId);

    OwnerEntity findByRoleId(UUID roleId);

    OwnerEntity findByDiscountId(UUID discountId);

}
