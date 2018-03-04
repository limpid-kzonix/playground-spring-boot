package com.omniesoft.commerce.user.service.account.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.SecurityModuleErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserPasswordResetEntity;
import com.omniesoft.commerce.persistence.repository.account.UserPasswordRepository;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.user.controller.account.payload.password.ChangePassword;
import com.omniesoft.commerce.user.controller.account.payload.password.PasswordResetDto;
import com.omniesoft.commerce.user.controller.account.payload.password.ResetPassword;
import com.omniesoft.commerce.user.controller.account.payload.password.ResetPasswordCode;
import com.omniesoft.commerce.user.service.account.AccountPasswordService;
import com.omniesoft.commerce.user.service.util.event.account.events.OnChangePasswordEvent;
import com.omniesoft.commerce.user.service.util.event.account.events.OnForgotPasswordEvent;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountPasswordServiceImpl implements AccountPasswordService {

    private final ApplicationEventPublisher eventPublisher;

    private final UserPasswordRepository userPasswordRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public void setPasswordResetCode(String email) {

        UserEntity userEntity = getAndCheckUser(email);

        if (userEntity.getEnabled()) {
            throw new UsefulException("User with such email is not enabled", UserModuleErrorCodes.USER_NOT_ACTIVE);
        }

        UserPasswordResetEntity userPasswordResetEntity = preformResetPassword(userEntity);
        userPasswordRepository.deleteByUserEmail(email);
        UserPasswordResetEntity saved = userPasswordRepository.save(userPasswordResetEntity);
        eventPublisher.publishEvent(new OnForgotPasswordEvent(saved));
    }

    private UserEntity getAndCheckUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsefulException("User with such email does not exist", UserModuleErrorCodes.USER_NOT_EXIST);
        }
        return userEntity;
    }

    @Override
    public PasswordResetDto verifyResetPasswordCode(ResetPasswordCode resetPasswordCode) {

        UserPasswordResetEntity byUserEmailAndCode = userPasswordRepository
                .findByUserEmailAndCode(resetPasswordCode.getEmail(), resetPasswordCode.getCode());
        checkPasswordEntity(byUserEmailAndCode);
        byUserEmailAndCode.setResetToken(generateResetToken());
        userPasswordRepository.save(byUserEmailAndCode);

        return modelMapper.map(byUserEmailAndCode, PasswordResetDto.class);
    }

    private void checkPasswordEntity(UserPasswordResetEntity byUserEmailAndCode) {

        if (byUserEmailAndCode == null) {
            throw new UsefulException("Invalid credentials", SecurityModuleErrorCodes.INVALID_CLIENT_CREDENTIALS);
        }

    }

    private void validatePasswordEntity(UserPasswordResetEntity byUserEmailAndCode) {

        checkPasswordEntity(byUserEmailAndCode);
        if (LocalDateTime.now().isAfter(byUserEmailAndCode.getExpireTime())) {
            throw new UsefulException("", SecurityModuleErrorCodes.INVALID_CLIENT_CREDENTIALS);
        }
    }

    @Override
    public void resetPassword(ResetPassword resetPassword) {

        UserPasswordResetEntity byUserEmailAndResetToken = userPasswordRepository
                .findWithUser(resetPassword.getUserEmail(), resetPassword.getToken());
        checkPasswordEntity(byUserEmailAndResetToken);
        validatePasswordEntity(byUserEmailAndResetToken);
        saveUserWithNewPassword(byUserEmailAndResetToken.getUser(), resetPassword.getNewPassword());
    }

    @Override
    public void changePassword(ChangePassword changePassword, UserEntity userEntity) {

        if (isMatches(changePassword, userEntity)) {
            saveUserWithNewPassword(userEntity, changePassword.getNewPassword());
        } else {
            throw new UsefulException("Incorrect credentials",
                    SecurityModuleErrorCodes.INVALID_CLIENT_CREDENTIALS);
        }

    }

    private void saveUserWithNewPassword(UserEntity userEntity, String password) {

        userRepository.save(prepareUserWithNewPassword(userEntity, password));
        userPasswordRepository.deleteByUserEmail(userEntity.getEmail());
        eventPublisher.publishEvent(new OnChangePasswordEvent(userEntity.getEmail(), userEntity.getLogin()));
    }

    private boolean isMatches(ChangePassword changePassword, UserEntity userEntity) {

        return passwordEncoder.matches(changePassword.getCurrentPassword(), userEntity.getEncryptPassword());
    }

    private UserEntity prepareUserWithNewPassword(UserEntity user, String pass) {

        user.setEncryptPassword(passwordEncoder.encode(pass));
        user.setLastPasswordResetDate(LocalDateTime.now());
        return user;
    }

    private UserPasswordResetEntity preformResetPassword(UserEntity userEntity) {

        UserPasswordResetEntity userPasswordResetEntity = new UserPasswordResetEntity();
        userPasswordResetEntity.setCode(generateCode());
        userPasswordResetEntity.setUser(userEntity);
        userPasswordResetEntity.setExpireTime(LocalDateTime.now().plusMinutes(30));
        return userPasswordResetEntity;
    }

    private Integer generateCode() {

        return RandomUtils.nextInt(1000, 9999);
    }

    private String generateResetToken() {

        return RandomString.make(15);
    }
}
