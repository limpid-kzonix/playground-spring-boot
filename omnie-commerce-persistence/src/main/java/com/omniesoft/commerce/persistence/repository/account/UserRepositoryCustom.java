package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 04.08.17
 */
public interface UserRepositoryCustom {
    UserEntity merge(UserEntity user);

    UserEntity findWithProfile(UUID id);

    UserEntity findWithProfile(String username);

    UserEntity getUserSecurityDetails(String id);


}
