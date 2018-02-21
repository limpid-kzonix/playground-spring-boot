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

import com.omniesoft.commerce.common.converter.SubServicePriceConverter;
import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServicePriceEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubServicePriceConverterImpl implements SubServicePriceConverter {

    @Override
    public List<SubServicePayload> convert(List<SubServiceEntity> subServiceEntities, LocalDateTime time) {

        return subServiceEntities.stream()
                .flatMap(
                        // wrap result to stream
                        subServiceEntity -> Stream.of(subServicePrice(time, subServiceEntity))
                                .filter(Objects::nonNull)
                                // map sub-service-price  and sub-service item to
                                // result type objects
                                .map(subServicePriceEntity -> payload(
                                        subServiceEntity, subServicePriceEntity)))
                .filter(distinctByKey(e -> e.getId()))
                .collect(Collectors.toList());
    }

    private SubServicePriceEntity subServicePrice(LocalDateTime time, SubServiceEntity subServiceEntity) {
        // get sub-service service prices available dependent on input datetime
        return subServiceEntity.getPrices()
                .stream()
                //filter for getting prices with activeFrom date less than input
                .filter(findAvailablePrice(time))
                .filter(Objects::nonNull)
                //and find last of this date
                .max(Comparator.comparing(
                        SubServicePriceEntity::getActiveFrom))
                //
                .get();
    }

    private Predicate<SubServicePriceEntity> findAvailablePrice(LocalDateTime time) {

        return e -> e.getActiveFrom()
                .isBefore(time.plus(1,
                        ChronoUnit.MILLIS));
    }

    private SubServicePayload payload(SubServiceEntity subServiceEntity, SubServicePriceEntity subServicePriceEntity) {

        return new SubServicePayload(
                subServiceEntity.getId(),
                subServiceEntity.getName(),
                subServicePriceEntity.getActiveFrom(),
                subServicePriceEntity.getMeasurementUnit(),
                subServicePriceEntity.getDurationMillis(),
                subServicePriceEntity.getExpense(),
                subServicePriceEntity.getPrice(),
                subServicePriceEntity.getMinCount(),
                subServicePriceEntity.getMaxCount(),
                subServicePriceEntity.getMaxDiscount()
        );
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
