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
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.omniesoft.commerce.common.converter.ServicePriceConverter;
import com.omniesoft.commerce.common.converter.ServicePriceListConverter;
import com.omniesoft.commerce.common.payload.service.ServicePayload;
import com.omniesoft.commerce.common.payload.service.ServiceSubCategoryPayload;
import com.omniesoft.commerce.common.payload.service.ServiceWithPricePayload;
import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServicePriceListConverterImpl implements ServicePriceListConverter {

    private final ServicePriceConverter servicePriceConverter;

    // Before changes this map has structure with key
    @Override
    public Map<UUID, List<ServiceWithPricePayload>> toPriceList(
            List<ServiceEntity> serviceEntities) {

        List<ServiceWithPricePayload> collect = transform(serviceEntities);

        return convertToMap(collect);

    }

    private List<ServiceWithPricePayload> transform(
            List<ServiceEntity> serviceEntities) {

        return serviceEntities.stream()
                .flatMap(serviceEntity -> serviceEntity
                        .getSubCategories()
                        .stream().map(subCategoryEntity -> {
                            return toServicePriceList(serviceEntity,
                                    subCategoryEntity);
                        })
                ).collect(Collectors.toList());
    }

    private Map<UUID, List<ServiceWithPricePayload>> convertToMap(
            List<ServiceWithPricePayload> collect) {

        ImmutableListMultimap<UUID, ServiceWithPricePayload> index = toMultimap(collect);
        return Multimaps
                .asMap(index);
    }

    private ImmutableListMultimap<UUID, ServiceWithPricePayload> toMultimap(
            List<ServiceWithPricePayload> collect) {

        return Multimaps
                .index(collect, s -> s.getSubCategoryPayload().getId());
    }

    private ServiceWithPricePayload toServicePriceList(ServiceEntity serviceEntity,
                                                       SubCategoryEntity subCategoryEntity) {

        return new ServiceWithPricePayload(
                new ServicePayload(serviceEntity.getId(), serviceEntity.getName(), serviceEntity.getLogoId()),
                new ServiceSubCategoryPayload(
                        subCategoryEntity.getId(),
                        subCategoryEntity.getCategory().getId(),
                        subCategoryEntity.getRusName(),
                        subCategoryEntity.getUkrName(),
                        subCategoryEntity.getEngName()
                ),
                servicePriceConverter.toTimeSheet(Lists.newArrayList(serviceEntity.getPrices()))

        );
    }
}
