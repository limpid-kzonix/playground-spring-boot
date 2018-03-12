package com.omniesoft.commerce.user.service.account.impl;

import com.google.common.collect.Lists;
import com.omniesoft.commerce.common.payload.account.AccountDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserSettingEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.user.controller.account.payload.UserRegisterPayload;
import com.omniesoft.commerce.user.service.account.AccountService;
import com.omniesoft.commerce.user.service.account.AuthorityService;
import com.omniesoft.commerce.user.service.util.event.account.events.OnRegistrationCompleteEvent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.omniesoft.commerce.persistence.entity.enums.AuthorityName.ROLE_USER;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final ModelMapper modelMapper;

    @Override
    public AccountDto register(UserRegisterPayload user) {
        UserEntity userEntity = prepareUserEntity(user);
        UserEntity saved = userRepository.save(userEntity);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(saved));
        return modelMapper.map(saved, AccountDto.class);
    }

    @Override
    public UserEntity getUserInfo(String username) {
        return userRepository.findByLoginOrEmail(username, username);
    }

    private UserEntity prepareUserEntity(UserRegisterPayload user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(user.getLogin().toLowerCase());
        userEntity.setAuthorities(Lists.newArrayList(authorityService.getAuthority(ROLE_USER)));
        userEntity.setEmail(user.getEmail().toLowerCase());
        userEntity.setEncryptPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setLastPasswordResetDate(LocalDateTime.now());
        userEntity.setRegistrationDate(LocalDateTime.now());
        userEntity.setEnabled(false);
        UserSettingEntity userSettingEntity = prepareUserSetting();
        userEntity.setSetting(userSettingEntity);
        return userEntity;
    }

    private UserSettingEntity prepareUserSetting() {
        UserSettingEntity userSettingEntity = new UserSettingEntity();
        userSettingEntity.setBackgroundNotification(true);
        userSettingEntity.setCalendarSync(true);
        userSettingEntity.setCreateTime(LocalDateTime.now());
        userSettingEntity.setEventNotification(true);
        userSettingEntity.setLightTheme(false);
        userSettingEntity.setPushNotification(true);
        userSettingEntity.setNotificationDelay(15);
        userSettingEntity.setSoundNotification(true);
        return userSettingEntity;
    }
}
