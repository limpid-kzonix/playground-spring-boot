package com.omniesoft.commerce.security.social.service.oauth.impl.facebook;

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
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service("facebookApiExplorer")
public class FacebookApiExplorerImpl implements OAuthApiExplorer {

    private final OAuthProfileValidator oAuthProfileValidator;

    private final ModelMapper modelMapper;

    private final OAuthConnectionWrappedFactory connectionWrappedFactory;

    public FacebookApiExplorerImpl(
            OAuthProfileValidator oAuthProfileValidator,
            ModelMapper modelMapper,
            @Qualifier("facebookConnectionWrappedFactory") OAuthConnectionWrappedFactory connectionWrappedFactory) {

        this.oAuthProfileValidator = oAuthProfileValidator;
        this.modelMapper = modelMapper;
        this.connectionWrappedFactory = connectionWrappedFactory;
    }

    @Override
    public SocialProfile fetchUserProfile(String oAuthToken) {

        Connection<Facebook> connect = (Connection<Facebook>) connectionWrappedFactory
                .connect(new AccessGrant(oAuthToken));

        return getSocialProfile(connect);

    }

    private SocialProfile getSocialProfile(Connection<Facebook> connect) {

        User facebookProfile = connect.getApi().fetchObject(FbHelper.me, User.class, FbHelper.fields);
        if (facebookProfile == null) throw new UsefulException("Can`t fetch user profile info from social provider",
                SecurityModuleErrorCodes.SOCIAL_FACEBOOK_PROFILE_NOT_EXIST);
        SocialProfile socialProfile = extractSocialProfile(facebookProfile);
        oAuthProfileValidator.validate(socialProfile);
        return socialProfile;
    }

    private SocialProfile extractSocialProfile(User facebookProfile) {

        SocialProfile socialProfile = modelMapper.map(facebookProfile, SocialProfile.class);
        socialProfile = extractAdditionalField(socialProfile, facebookProfile);
        return socialProfile;
    }

    private SocialProfile extractAdditionalField(SocialProfile socialProfile, User facebookProfile) {

        setGender(socialProfile, facebookProfile);
        setBirthDay(socialProfile, facebookProfile);
        return socialProfile;
    }

    private void setGender(SocialProfile socialProfile, User facebookProfile) {

        if (facebookProfile != null && facebookProfile.getGender() != null) {
            try {
                Gender gender = EnumLookupUtil.lookup(Gender.class, facebookProfile.getGender().toUpperCase());
                socialProfile.setGender(gender);
            } catch (Exception e) {
                throw new UsefulException("Social profile value of 'gender' field is invalid", SecurityModuleErrorCodes.SOCIAL_GENDER_INCORRECT);
            }
        }
    }

    private void setBirthDay(SocialProfile socialProfile, User facebookProfile) {

        if (facebookProfile != null && facebookProfile.getBirthday() != null) {
            try {
                LocalDate parsed = LocalDate.parse(facebookProfile.getBirthday(), DateTimeFormatter.ofPattern
                        ("MM/dd/yyyy"));
                socialProfile.setBirthDate(parsed);
            } catch (Exception e) {
                throw new UsefulException("Social profile value of 'birthDate' field is invalid", SecurityModuleErrorCodes.SOCIAL_BIRTHDATE_INCORRECT);
            }

        }
    }
}
