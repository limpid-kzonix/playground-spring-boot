package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserOAuthEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.persistence.projection.account.UserOAuthSummary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserOAuthRepository extends CrudRepository<UserOAuthEntity, UUID> {

    UserOAuthEntity findByUserLoginAndOauthClient(String userName, OAuthClient client);

    UserOAuthEntity findByProfileIdAndOauthClient(String profileId, OAuthClient client);

    List<UserOAuthSummary> findAllByUserId(UUID userId);

}
