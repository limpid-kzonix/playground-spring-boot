package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserSettingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserSettingsRepository extends CrudRepository<UserSettingEntity, UUID> {

}
