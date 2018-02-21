package com.omniesoft.commerce.security.social.service.oauth.impl.google;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.enums.Gender;
import com.omniesoft.commerce.security.social.service.oauth.OAuthApiExplorer;
import com.omniesoft.commerce.security.social.service.oauth.OAuthConnectionWrappedFactory;
import com.omniesoft.commerce.security.social.service.oauth.OAuthProfileValidator;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import com.omniesoft.commerce.security.social.service.util.EnumLookupUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Service("googleApiExplorer")
public class GoogleApiExplorerImpl implements OAuthApiExplorer {

    private final OAuthProfileValidator oAuthProfileValidator;

    private final ModelMapper modelMapper;

    private final OAuthConnectionWrappedFactory connectionWrappedFactory;

    public GoogleApiExplorerImpl(
            OAuthProfileValidator oAuthProfileValidator,
            ModelMapper modelMapper,
            @Qualifier("googleConnectionWrappedFactory") OAuthConnectionWrappedFactory connectionWrappedFactory) {

        this.oAuthProfileValidator = oAuthProfileValidator;
        this.modelMapper = modelMapper;
        this.connectionWrappedFactory = connectionWrappedFactory;
    }

    @Override
    public SocialProfile fetchUserProfile(String oAuthToken) {
        Connection<Google> connect = (Connection<Google>) connectionWrappedFactory.connect(new AccessGrant(oAuthToken));
        return getSocialProfile(connect);

    }

    private SocialProfile getSocialProfile(Connection<Google> connect) {

        GoogleUserInfo googleUserInfo = connect.getApi().userOperations().getUserInfo();
        Person googleProfile = connect.getApi().plusOperations().getGoogleProfile();

        if (!(googleUserInfo != null || googleProfile != null))
            throw new UsefulException("Can`t fetch user profile info from social provider",
                    SecurityModuleErrorCodes.SOCIAL_GOOGLE_PROFILE_NOT_EXIST);

        SocialProfile userProfile = extractSocialProfile(googleUserInfo, googleProfile);
        oAuthProfileValidator.validate(userProfile);
        return userProfile;
    }

    private SocialProfile extractSocialProfile(GoogleUserInfo googleUserInfo, Person googleProfile) {

        SocialProfile socialProfile = modelMapper.map(googleUserInfo, SocialProfile.class);
        socialProfile = extractAdditionalField(socialProfile, googleUserInfo, googleProfile);
        return socialProfile;
    }

    private SocialProfile extractAdditionalField(SocialProfile socialProfile, GoogleUserInfo facebookProfile,
                                                 Person person) {

        getGender(socialProfile, facebookProfile);
        getBirthDay(socialProfile, person);
        return socialProfile;
    }

    private void getBirthDay(SocialProfile socialProfile, Person person) {

        if (person != null && person.getBirthday() != null) {
            try {
                LocalDate birthDate = person.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                socialProfile.setBirthDate(birthDate);
            } catch (Exception e) {
                throw new UsefulException("Social profile value of 'birthDate' field is invalid", SecurityModuleErrorCodes.SOCIAL_BIRTHDATE_INCORRECT);
            }

        }
    }

    private void getGender(SocialProfile socialProfile, GoogleUserInfo googleUserInfo) {

        if (googleUserInfo != null && googleUserInfo.getGender() != null) {
            try {
                Gender gender = EnumLookupUtil.lookup(Gender.class, googleUserInfo.getGender().toUpperCase());
                socialProfile.setGender(gender);
            } catch (Exception e) {
                throw new UsefulException("Social profile value of 'gender' field is invalid", SecurityModuleErrorCodes.SOCIAL_GENDER_INCORRECT);
            }

        }
    }
}
