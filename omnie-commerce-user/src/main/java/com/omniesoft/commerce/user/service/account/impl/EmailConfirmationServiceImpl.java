package com.omniesoft.commerce.user.service.account.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEmailVerificationEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserEmailVerificationRepository;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.user.service.account.EmailConfirmationService;
import com.omniesoft.commerce.user.service.util.event.account.events.OnReplyRegistrationConfirmationEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private final UserEmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public void setEmailToken(UserEntity userEntity, String token) {

        UserEmailVerificationEntity userEmailVerificationEntity = new UserEmailVerificationEntity();
        userEmailVerificationEntity.setConfirmStatus(false);
        userEmailVerificationEntity.setToken(token);
        userEmailVerificationEntity.setUser(userEntity);
        emailVerificationRepository.save(userEmailVerificationEntity);
    }

    @Override
    @Transactional
    public boolean verifyEmailToken(String token) {

        UserEmailVerificationEntity byToken = emailVerificationRepository.findByToken(token);
        checkEmailToken(token, byToken);
        UserEntity user = byToken.getUser();
        user.setEnabled(true);
        emailVerificationRepository.deleteByToken(token);
        userRepository.save(user);
        return true;
    }

    @Override
    public void replyEmailTokenSending(String email) {
        UserEntity byEmail = userRepository.findByEmail(email);
        if (byEmail == null) {
            throw new UsefulException("User with email [ " + email + " ] does not exist", UserModuleErrorCodes.USER_NOT_EXIST);
        }
        eventPublisher.publishEvent(new OnReplyRegistrationConfirmationEvent(byEmail));
    }

    private void checkEmailToken(String token, UserEmailVerificationEntity byToken) {

        if (null == byToken) {
            throw new UsefulException("Email confirmation is not available with token : " + token,
                    SecurityModuleErrorCodes.INVALID_EMAIL_TOKEN);
        }
    }
}
