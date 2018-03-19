package com.omniesoft.commerce.user.service.account.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEmailChangingVerificationEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserEmailChangingVerificationRepository;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.user.service.account.EmailChangingService;
import com.omniesoft.commerce.user.service.util.event.account.events.OnEmailChangeEvent;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmailChangingServiceImpl implements EmailChangingService {

    private final UserEmailChangingVerificationRepository userEmailChangingVerificationRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    @Override
    public void changeEmail(UserEntity userEntity, String newEmail) {
        UserEntity byEmail = userRepository.findByEmail(newEmail);
        if (byEmail != null) {
            throw new UsefulException("You can`t use this email").withCode(UserModuleErrorCodes.EMAIL_NOT_ALLOWED);
        }
        UserEmailChangingVerificationEntity byUserId = userEmailChangingVerificationRepository.findByUserId(userEntity.getId());
        String token;
        if (byUserId != null) {
            token = byUserId.getToken();
            byUserId.setEmail(newEmail);
        } else {
            token = RandomString.make(20).toLowerCase();
            byUserId = new UserEmailChangingVerificationEntity();
            byUserId.setEmail(newEmail);
            byUserId.setConfirmStatus(false);
            byUserId.setCreateTime(LocalDateTime.now());
            byUserId.setToken(token);
            byUserId.setUser(userEntity);
        }
        userEmailChangingVerificationRepository.save(byUserId);
        eventPublisher.publishEvent(new OnEmailChangeEvent(userEntity, newEmail, token));
    }

    @Override
    public void changeEmail(String token) {
        UserEmailChangingVerificationEntity byToken = userEmailChangingVerificationRepository.findByToken(token);
        if (byToken == null) {
            throw new UsefulException("Token is invalid").withCode(SecurityModuleErrorCodes.INVALID_EMAIL_TOKEN);
        }
        if (byToken.getCreateTime().plusHours(2).isBefore(LocalDateTime.now())) {
            userEmailChangingVerificationRepository.delete(byToken.getId());
            throw new UsefulException("Token is expired").withCode(SecurityModuleErrorCodes.EXPIRED_EMAIL_TOKEN);
        }

        UserEntity user = byToken.getUser();
        user.setEmail(byToken.getEmail());
        userRepository.save(user);
    }
}
