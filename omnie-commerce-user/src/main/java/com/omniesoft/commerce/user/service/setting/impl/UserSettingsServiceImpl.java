package com.omniesoft.commerce.user.service.setting.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserSettingEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.account.UserSettingsRepository;
import com.omniesoft.commerce.user.controller.setting.payload.UserSettingPayload;
import com.omniesoft.commerce.user.service.setting.UserSettingsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
@AllArgsConstructor
@CacheConfig(cacheNames = {"user_settings"})
public class UserSettingsServiceImpl implements UserSettingsService {

    private final UserRepository userRepository;

    private final UserSettingsRepository settingsRepository;

    private final ModelMapper modelMapper;


    @Override
//    @CacheEvict(value = "user_settings", key = "#userEntity.id")
    public UserSettingPayload update(UserEntity userEntity, UserSettingPayload payload) {

        UserSettingEntity userSettingEntity = prepareUserSettingEntity(payload);
        checkIfSettingIsPresent(userSettingEntity);
        userSettingEntity = this.setUserSetting(userEntity, userSettingEntity);
        return modelMapper.map(userSettingEntity, UserSettingPayload.class);
    }

    private UserSettingEntity prepareUserSettingEntity(UserSettingPayload payload) {

        UserSettingEntity userSettingEntity = new UserSettingEntity();
        userSettingEntity.setSoundNotification(payload.getSoundNotification());
        userSettingEntity.setNotificationDelay(payload.getNotificationDelay());
        userSettingEntity.setPushNotification(payload.getPushNotification());
        userSettingEntity.setLightTheme(payload.getLightTheme());
        userSettingEntity.setEventNotification(payload.getEventNotification());
        userSettingEntity.setCalendarSync(payload.getCalendarSync());
        userSettingEntity.setBackgroundNotification(payload.getBackgroundNotification());
        return userSettingEntity;
    }

    @Override
    @Cacheable(value = "user_settings", key = "#userEntity.id")
    public UserSettingPayload find(UserEntity userEntity) {

        UserSettingEntity one = settingsRepository.findOne(userEntity.getSetting().getId());
        checkIfSettingIsPresent(one);
        return modelMapper.map(one, UserSettingPayload.class);
    }

    private void checkIfSettingIsPresent(UserSettingEntity one) {

        if (one == null) {
            throw new UsefulException(UserModuleErrorCodes.SETTINGS_NOT_EXIST).withCode(UserModuleErrorCodes.SETTINGS_NOT_EXIST);
        }
    }

    private UserSettingEntity setUserSetting(UserEntity userEntity, UserSettingEntity settingEntity) {

        userEntity.setSetting(settingEntity);
        UserEntity saved = userRepository.save(userEntity);
        return saved.getSetting();
    }
}
