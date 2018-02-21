package com.omniesoft.commerce.owner.service.service;

import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ServiceScopeTimeSheetService {


    Map<DayOfWeek, List<ServicePricePayload>> getServiceTimeSheet(UUID service, UUID org, UserEntity userEntity);

    void mergeDaysPrices(DayOfWeek day, List<ServicePricePayload> pricePayloads, UUID service, UUID org, UserEntity
            userEntity);

    void deleteDaysPrices(DayOfWeek day, UUID service, UUID org, UserEntity userEntity);


}
