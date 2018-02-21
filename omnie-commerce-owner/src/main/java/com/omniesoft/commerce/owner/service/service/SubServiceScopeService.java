package com.omniesoft.commerce.owner.service.service;

import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.util.List;
import java.util.UUID;

public interface SubServiceScopeService {

    SubServicePayload save(SubServicePayload subServicePayload, UUID org, UUID service, UserEntity userEntity);

    SubServicePayload update(SubServicePayload subServicePayload, UUID org, UUID service, UUID subService, UserEntity
            userEntity);

    List<SubServicePayload> findSubServiceByService(UUID org, UUID service, UserEntity userEntity);

    void deleteSubService(UUID subServiceId, UUID service, UUID org, UserEntity userEntity);


}
