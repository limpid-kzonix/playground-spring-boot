package com.omniesoft.commerce.security.config;

import com.google.common.collect.Lists;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserSettingEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.security.jwt.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static com.omniesoft.commerce.persistence.entity.enums.AuthorityName.ROLE_SUPPORT_ADMIN;

@Slf4j
@Component
public class StarterTask {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    private final String supperUserLogin;
    private final String supperUserPassword;

    public StarterTask(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthorityService authorityService,
                       @Value("${super.user.login}") String supperUserLogin, @Value("${super.user.password}") String supperUserPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
        this.supperUserLogin = supperUserLogin;
        this.supperUserPassword = supperUserPassword;
    }


    @PostConstruct
    public void init() {
        UserEntity user = userRepository.findByLogin(supperUserLogin);
        if (user != null) {
            userRepository.delete(user.getId());
        }
        userRepository.save(getUserEntity(new UserEntity()));


    }

    private UserEntity getUserEntity(UserEntity byLogin) {
        UserEntity userEntity = byLogin;
        userEntity.setId(byLogin.getId());
        userEntity.setLogin(supperUserLogin);
        userEntity.setAuthorities(Lists.newArrayList(authorityService.getAuthority(ROLE_SUPPORT_ADMIN)));
        userEntity.setEmail("felgeate@omniecom.com");
        userEntity.setEncryptPassword(passwordEncoder.encode(supperUserPassword));
        userEntity.setLastPasswordResetDate(LocalDateTime.now());
        userEntity.setRegistrationDate(LocalDateTime.now());
        userEntity.setEnabled(true);
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
