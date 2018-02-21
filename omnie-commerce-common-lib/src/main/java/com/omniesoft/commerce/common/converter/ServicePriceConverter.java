package com.omniesoft.commerce.common.converter;

import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.persistence.entity.service.ServicePriceEntity;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface ServicePriceConverter {
    ServicePricePayload toServicePayload(ServicePriceEntity servicePriceEntity);

    Map<DayOfWeek, List<ServicePricePayload>> toTimeSheet(List<ServicePriceEntity> servicePriceEntities);

    Map<DayOfWeek, List<ScheduleSetting>> toSchedule(List<ServicePriceEntity> servicePriceEntities);

}
