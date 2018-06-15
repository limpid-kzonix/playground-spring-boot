package com.omniesoft.commerce.security.social.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserOAuthEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.persistence.repository.account.UserOAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FCMService implements IFCMService {
    private final UserOAuthRepository userOAuthRepo;

    @Override
    public void link(UserEntity user, String fcmToken) {
        UserOAuthEntity oAuthEntity = new UserOAuthEntity();
        oAuthEntity.setUser(user);
        oAuthEntity.setOauthClient(OAuthClient.FCM);
        oAuthEntity.setProfileId(fcmToken);

        userOAuthRepo.save(oAuthEntity);
    }
}
