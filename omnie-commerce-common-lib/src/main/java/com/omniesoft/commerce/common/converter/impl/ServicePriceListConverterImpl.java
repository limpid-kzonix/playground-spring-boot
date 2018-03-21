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
import com.omniesoft.commerce.common.payload.service.*;
import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServicePriceListConverterImpl implements ServicePriceListConverter {

    private final ServicePriceConverter servicePriceConverter;

    // Before changes this map has structure with key
    @Override
    public List<OrganizationPriceList> toPriceList(
            List<ServiceEntity> serviceEntities) {

        List<ServicePricePayloadByCategory> collect = transform(serviceEntities);

        Map<ServiceSubCategoryPayload, List<ServicePricePayloadByCategory>> serviceSubCategoryPayloadListMap = convertToMap(collect);
        return getOrganizationPriceList(serviceSubCategoryPayloadListMap);

    }

    private List<OrganizationPriceList> getOrganizationPriceList(Map<ServiceSubCategoryPayload, List<ServicePricePayloadByCategory>> serviceSubCategoryPayloadListMap) {
        return serviceSubCategoryPayloadListMap.entrySet().stream().map(e -> {
            ServiceSubCategoryPayload key = e.getKey();
            List<ServiceWithPricePayload> collected = e.getValue().stream().map(ServicePricePayloadByCategory::getServicePrices).collect(Collectors.toList());
            return new OrganizationPriceList(key, collected);
        }).collect(Collectors.toList());
    }

    private List<ServicePricePayloadByCategory> transform(
            List<ServiceEntity> serviceEntities) {

        return serviceEntities.stream()
                .flatMap(serviceEntity -> serviceEntity
                        .getSubCategories()
                        .stream().map(subCategoryEntity -> toServicePriceList(serviceEntity,
                                subCategoryEntity))
                ).collect(Collectors.toList());
    }

    private Map<ServiceSubCategoryPayload, List<ServicePricePayloadByCategory>> convertToMap(
            List<ServicePricePayloadByCategory> collect) {

        ImmutableListMultimap<ServiceSubCategoryPayload, ServicePricePayloadByCategory> index = toMultimap(collect);
        return Multimaps
                .asMap(index);
    }

    @SuppressWarnings("unchecked")
    private ImmutableListMultimap<ServiceSubCategoryPayload, ServicePricePayloadByCategory> toMultimap(
            List<ServicePricePayloadByCategory> collect) {

        return Multimaps
                .index(collect, ServicePricePayloadByCategory::getCategory);
    }

    private ServicePricePayloadByCategory toServicePriceList(ServiceEntity serviceEntity,
                                                             SubCategoryEntity subCategoryEntity) {
        ServiceSubCategoryPayload serviceSubCategoryPayload = new ServiceSubCategoryPayload(
                subCategoryEntity.getId(),
                subCategoryEntity.getCategory().getId(),
                subCategoryEntity.getRusName(),
                subCategoryEntity.getUkrName(),
                subCategoryEntity.getEngName()
        );
        ServiceWithPricePayload serviceWithPricePayload = new ServiceWithPricePayload(
                new ServicePayload(serviceEntity.getId(), serviceEntity.getName(), serviceEntity.getLogoId()),
                servicePriceConverter.toTimeSheet(Lists.newArrayList(serviceEntity.getPrices()))

        );
        return new ServicePricePayloadByCategory(serviceSubCategoryPayload, serviceWithPricePayload);
    }
}
