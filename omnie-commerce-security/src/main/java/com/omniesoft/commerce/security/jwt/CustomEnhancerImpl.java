package com.omniesoft.commerce.security.jwt;

import com.google.common.collect.Lists;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserProfileEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Component
public class CustomEnhancerImpl implements CustomEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        Object principal = oAuth2Authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            compactWithAdditionalInfo((DefaultOAuth2AccessToken) oAuth2AccessToken,
                    oAuth2Authentication, (UserDetails) principal);
        }
        return oAuth2AccessToken;
    }

    private void compactWithAdditionalInfo(DefaultOAuth2AccessToken oAuth2AccessToken,
                                           OAuth2Authentication oAuth2Authentication, UserDetails principal) {

        UserDetails userDetails = principal;
        // May use other methods for add necessary info to jwt
        UserEntity byLoginOrEmail = userRepository.findByLogin(userDetails.getUsername());


        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("userId", byLoginOrEmail.getId());
        additionalInfo.put("userEmail", byLoginOrEmail.getEmail());
        additionalInfo.put("roles", byLoginOrEmail.getAuthorities());
        verify(additionalInfo, byLoginOrEmail);
        oAuth2Authentication.setDetails(userDetails);
        oAuth2AccessToken.setAdditionalInformation(additionalInfo);
    }

    private void verify(Map<String, Object> additionalInfo, UserEntity userEntity) {

        UserProfileEntity profile = userEntity.getProfile();
        if (profile == null) {
            additionalInfo.put("exception", Lists.newArrayList(new JwtAdditionalPayload("profile", 2201, "need " +
                    "to fill profile")));
        } else {
            additionalInfo.put("omniecard", profile.getOmnieCard());
        }
    }


    @Data
    @AllArgsConstructor
    private class JwtAdditionalPayload {

        private String key;

        private Integer code;

        private Object value;
    }
}
