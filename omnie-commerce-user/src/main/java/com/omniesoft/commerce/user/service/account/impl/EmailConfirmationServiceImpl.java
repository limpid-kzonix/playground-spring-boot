package com.omniesoft.commerce.user.service.account.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEmailVerificationEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserEmailVerificationRepository;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.user.service.account.EmailConfirmationService;
import com.omniesoft.commerce.user.service.util.event.account.events.OnReplyRegistrationConfirmationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private final String frontEndAppUrl;

    private final UserEmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public EmailConfirmationServiceImpl(@Value("${application.host}") String frontEndAppUrl,
                                        UserEmailVerificationRepository emailVerificationRepository,
                                        UserRepository userRepository,
                                        ApplicationEventPublisher eventPublisher) {
        this.frontEndAppUrl = frontEndAppUrl;
        this.emailVerificationRepository = emailVerificationRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

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
    public void verifyEmailToken(String token) {
        UserEmailVerificationEntity byToken = emailVerificationRepository.findByToken(token);
        checkEmailToken(token, byToken);
    }

    private void redirect(String url) {
        try {
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse().sendRedirect(url);
        } catch (IOException e) {
            throw new UsefulException().withCode(InternalErrorCodes.INTERNAL);
        }
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
            String url = frontEndAppUrl + "/error/?errorCode=" + SecurityModuleErrorCodes.INVALID_EMAIL_TOKEN.getCode();
            redirect(url);
        } else {
            UserEntity user = byToken.getUser();
            user.setEnabled(true);
            emailVerificationRepository.deleteByToken(token);
            userRepository.save(user);
            redirect(frontEndAppUrl);
        }
    }
}
