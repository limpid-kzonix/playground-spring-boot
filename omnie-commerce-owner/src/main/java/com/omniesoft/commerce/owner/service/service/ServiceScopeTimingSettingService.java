package com.omniesoft.commerce.owner.service.service;

import com.omniesoft.commerce.owner.controller.service.payload.ServiceTimingPayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.util.UUID;

public interface ServiceScopeTimingSettingService {

    ServiceTimingPayload setServiceTimingSetting(ServiceTimingPayload payload, UUID org, UUID service,
                                                 UserEntity userEntity);

    ServiceTimingPayload getServiceTimingSetting(UUID org, UUID service,
                                                 UserEntity userEntity);


}
