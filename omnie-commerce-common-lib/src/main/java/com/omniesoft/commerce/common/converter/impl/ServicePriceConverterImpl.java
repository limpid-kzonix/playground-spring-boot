/*
 * Copyright (c)  2017
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.common.converter.impl;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.omniesoft.commerce.common.converter.ServicePriceConverter;
import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.persistence.entity.service.ServicePriceEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ServicePriceConverterImpl implements ServicePriceConverter {

    private ModelMapper modelMapper;


    @Override
    public ServicePricePayload toServicePayload(ServicePriceEntity servicePriceEntity) {
        ServicePricePayload servicePricePayload = new ServicePricePayload();
        servicePricePayload.setPrice(servicePriceEntity.getPrice());
        servicePricePayload.setPriceUnit(servicePriceEntity.getPriceUnit());
        servicePricePayload.setExpense(servicePriceEntity.getExpense());
        servicePricePayload.setEnd(servicePriceEntity.getEnd());
        servicePricePayload.setDay(servicePriceEntity.getDay());
        servicePricePayload.setStart(servicePriceEntity.getStart());
        servicePricePayload.setCreateTime(servicePriceEntity.getCreateTime());
        servicePricePayload.setActiveFrom(servicePriceEntity.getActiveFrom());
        return servicePricePayload;
    }

    @Override
    public Map<DayOfWeek, List<ServicePricePayload>> toTimeSheet(List<ServicePriceEntity> servicePriceEntities) {
        ImmutableListMultimap<DayOfWeek, ServicePriceEntity> index = index(servicePriceEntities);
        ListMultimap<DayOfWeek, ServicePricePayload> dayServicePricePayloadListMultimap = transformValues(index);
        return Multimaps.asMap
                (dayServicePricePayloadListMultimap);
    }

    @Override
    public Map<DayOfWeek, List<ScheduleSetting>> toSchedule(List<ServicePriceEntity> servicePrices) {
        ImmutableListMultimap<DayOfWeek, ServicePriceEntity> index = index(servicePrices);
        ListMultimap<DayOfWeek, ScheduleSetting> dayServicePricePayloadListMultimap = transformScheduleSettingValues(index);
        return Multimaps.asMap
                (dayServicePricePayloadListMultimap);
    }


    private ListMultimap<DayOfWeek, ServicePricePayload> transformValues(
            ImmutableListMultimap<DayOfWeek, ServicePriceEntity> index) {
        return Multimaps
                .transformValues(index, this::toServicePayload);
    }

    private ListMultimap<DayOfWeek, ScheduleSetting> transformScheduleSettingValues(
            ImmutableListMultimap<DayOfWeek, ServicePriceEntity> index) {
        return Multimaps.transformValues(index, this::toServicePayload);
    }

    private ImmutableListMultimap<DayOfWeek, ServicePriceEntity> index(List<ServicePriceEntity> servicePriceEntities) {
        return Multimaps.index(
                servicePriceEntities,
                ServicePriceEntity::getDay
        );
    }
}
