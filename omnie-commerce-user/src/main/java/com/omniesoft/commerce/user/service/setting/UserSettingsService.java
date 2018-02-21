package com.omniesoft.commerce.user.service.setting;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.setting.payload.UserSettingPayload;

public interface UserSettingsService {

    UserSettingPayload update(UserEntity userEntity, UserSettingPayload payload);

    UserSettingPayload find(UserEntity userEntity);
}
