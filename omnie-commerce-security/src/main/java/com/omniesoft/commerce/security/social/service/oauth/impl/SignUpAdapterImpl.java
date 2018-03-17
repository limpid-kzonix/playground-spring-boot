package com.omniesoft.commerce.security.social.service.oauth.impl;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserProfileEntity;
import com.omniesoft.commerce.persistence.entity.account.UserSettingEntity;
import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import com.omniesoft.commerce.persistence.repository.account.UserProfileRepository;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.security.social.service.oauth.OAuthAccountBindingService;
import com.omniesoft.commerce.security.social.service.oauth.SignUpAdapter;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SignUpAdapterImpl implements SignUpAdapter {

    private final OAuthAccountBindingService oAuthAccountBindingService;
    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public UserEntity singUp(OAuthClient client, SocialProfile socialProfile) {

        UserProfileEntity userProfileEntity = extractFromSocialProfile(socialProfile);
//        UserProfileEntity savedProfile = userProfileRepository.save(userProfileEntity);

        UserEntity userEntity = getUserEntity(socialProfile, userProfileEntity, client);
        UserEntity savedUser = userRepository.save(userEntity);
        oAuthAccountBindingService.link(savedUser, socialProfile, client);
        return savedUser;
    }

    private UserEntity getUserEntity(SocialProfile socialProfile, UserProfileEntity savedProfile, OAuthClient client) {

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin((client.name().toLowerCase() + "_" + RandomStringUtils.randomAlphabetic(10)).toLowerCase());
        userEntity.setEncryptPassword(encoder.encode(RandomStringUtils.randomAlphabetic(6)));
        userEntity.setRegistrationDate(LocalDateTime.now());
        userEntity.setLastPasswordResetDate(LocalDateTime.now());
        userEntity.setEmail(socialProfile.getEmail());
        userEntity.setEnabled(true);
        userEntity.setSetting(new UserSettingEntity());
        userEntity.setProfile(savedProfile);
        return userEntity;
    }

    private UserProfileEntity extractFromSocialProfile(SocialProfile socialProfile) {

        UserProfileEntity userProfileEntity = new UserProfileEntity();
        userProfileEntity.setFirstName(socialProfile.getFirstName());
        userProfileEntity.setLastName(socialProfile.getLastName());
        userProfileEntity.setBirthday(socialProfile.getBirthDate());
        userProfileEntity.setGender(socialProfile.getGender());
        userProfileEntity.setOmnieCard(generateOmnieCard());
        return userProfileEntity;
    }


    private String generateOmnieCard() {

        return "omnie#" + (int) ((System.nanoTime() / Math.PI) / (RandomUtils.nextInt(30000, 99999) * Math.E));
    }

}
