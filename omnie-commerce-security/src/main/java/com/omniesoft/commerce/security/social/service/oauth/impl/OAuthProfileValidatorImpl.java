package com.omniesoft.commerce.security.social.service.oauth.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.common.util.Patterns;
import com.omniesoft.commerce.security.social.service.oauth.OAuthProfileValidator;
import com.omniesoft.commerce.security.social.service.oauth.model.SocialProfile;
import org.springframework.stereotype.Service;

@Service
public class OAuthProfileValidatorImpl implements OAuthProfileValidator {


    @Override
    public void validate(SocialProfile socialProfile) {

        if (socialProfile == null) {
            throw new UsefulException("Can`t fetch user profile info from social provider", SecurityModuleErrorCodes
                    .SOCIAL_CANT_FETCH);
        }
        checkId(socialProfile);
        checkUserEmail(socialProfile);
        checkFirstName(socialProfile);
        checkLastName(socialProfile);

    }

    private void checkUserEmail(SocialProfile userProfile) {

        if (userProfile.getEmail() == null || !userProfile.getEmail().matches(Patterns.EMAIL)) {
            throw new UsefulException(
                    "Field is not valid: email (from social provider) is null or not compliance with pattern:" +
                            Patterns.EMAIL, SecurityModuleErrorCodes.SOCIAL_EMAIL_INCORRECT);
        }
    }

    private void checkFirstName(SocialProfile userProfile) {

        if (userProfile.getFirstName() == null) {
            throw new UsefulException("Field is not valid: first name (from social provider) is null",
                    SecurityModuleErrorCodes.SOCIAL_FIRST_NAME_INCORRECT);
        }
    }

    private void checkLastName(SocialProfile userProfile) {

        if (userProfile.getLastName() == null) {
            throw new UsefulException("Field is not valid: last name (from social provider) is null",
                    SecurityModuleErrorCodes
                            .SOCIAL_LAST_NAME_INCORRECT);
        }
    }

    private void checkId(SocialProfile userProfile) {

        if (userProfile.getId() == null) {
            throw new UsefulException("Field is not valid: id (from social provider) is null",
                    SecurityModuleErrorCodes.SOCIAL_ID_INCORRECT);
        }
    }


}
