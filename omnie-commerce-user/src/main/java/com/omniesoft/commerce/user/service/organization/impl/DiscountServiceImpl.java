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

package com.omniesoft.commerce.user.service.organization.impl;

import com.omniesoft.commerce.common.payload.discount.DiscountPayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.persistence.repository.organization.DiscountRepository;
import com.omniesoft.commerce.user.controller.organization.payload.OrganizationDiscountPayload;
import com.omniesoft.commerce.user.controller.organization.payload.ServiceDiscountPayload;
import com.omniesoft.commerce.user.service.organization.DiscountExtractorService;
import com.omniesoft.commerce.user.service.organization.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    private final DiscountExtractorService discountExtractorService;


    @Override
    public OrganizationDiscountPayload findOrganizationDiscounts(UUID organizationId, UserEntity userEntity) {

        List<DiscountEntity> allOrgDiscounts = discountRepository
                .findFullDiscountsDataByOrganizationId(organizationId);
        List<DiscountPayload> usersDiscountPayloads = getUserDiscountPayloads(userEntity, allOrgDiscounts);
        List<DiscountPayload> allDiscountPayloads = getAllDiscountPayloads(allOrgDiscounts, usersDiscountPayloads);
        return new OrganizationDiscountPayload(allDiscountPayloads, usersDiscountPayloads);
    }


    @Override
    public ServiceDiscountPayload findServiceDiscounts(UUID organizationId, UUID serviceId, UserEntity userEntity) {

        List<DiscountEntity> byServiceIdForUser = discountRepository
                .findFullDiscountDataByServiceId(organizationId, serviceId, userEntity);
        List<DiscountPayload> usersDiscountPayloads = getUserDiscountPayloads(userEntity, byServiceIdForUser);
        List<DiscountPayload> allDiscountPayloads = getAllDiscountPayloads(byServiceIdForUser, usersDiscountPayloads);
        return new ServiceDiscountPayload(allDiscountPayloads, usersDiscountPayloads);
    }

    private List<DiscountPayload> getAllDiscountPayloads(List<DiscountEntity> byServiceIdForUser,
                                                         List<DiscountPayload> usersDiscountPayloads) {

        List<DiscountPayload> allDiscountPayloads = discountExtractorService.extractDiscount(byServiceIdForUser);
        allDiscountPayloads.removeAll(usersDiscountPayloads);
        return allDiscountPayloads;
    }

    private List<DiscountPayload> getUserDiscountPayloads(UserEntity userEntity,
                                                          List<DiscountEntity> allOrgDiscounts) {

        return discountExtractorService
                .extractUsersDiscount(allOrgDiscounts, userEntity);
    }

    @Override
    public DiscountEntity findMaxServiceDiscount(OrderEntity orderEntity) {
        List<DiscountEntity> discounts = discountRepository
                .findNotPersonalByServiceAndActiveDate(orderEntity.getService().getId(), orderEntity.getStart());

        if (orderEntity.getUser() != null) {
            List<DiscountEntity> personalDiscounts = discountRepository.findPersonalByUserAndServiceAndActiveDate(
                    orderEntity.getUser().getId(),
                    orderEntity.getService().getId(),
                    orderEntity.getStart());

            if (discounts == null) {
                discounts = personalDiscounts;
            } else {
                discounts.addAll(personalDiscounts);
            }
        }

        if (discounts == null || discounts.isEmpty()) {
            return null;
        }

        return discounts
                .stream()
                .max(Comparator.comparing(DiscountEntity::getPercent))
                .get();

    }

    @Override
    public DiscountEntity findMaxSubServiceDiscount(OrderSubServicesEntity subService, OrderEntity orderEntity) {

        List<DiscountEntity> discounts = discountRepository
                .findNotPersonalBySubServiceAndActiveDate(subService.getId(), orderEntity.getStart());

        if (orderEntity.getUser() != null) {
            List<DiscountEntity> personalDiscounts = discountRepository.findPersonalByUserAndSubServiceAndActiveDate(
                    orderEntity.getUser().getId(),
                    subService.getId(),
                    orderEntity.getStart());

            if (discounts == null) {
                discounts = personalDiscounts;
            } else {
                discounts.addAll(personalDiscounts);
            }
        }

        if (discounts == null || discounts.isEmpty()) {
            return null;
        }

        return discounts
                .stream()
                .max(Comparator.comparing(DiscountEntity::getPercent))
                .get();
    }

    @Override
    public Map<UUID, DiscountEntity> findMaxSubServicesDiscounts(OrderEntity orderEntity) {

        Map<UUID, DiscountEntity> result = new HashMap<>();

        if (orderEntity != null && orderEntity.getSubServices() != null) {
            for (OrderSubServicesEntity subService : orderEntity.getSubServices()) {

                DiscountEntity subServiceDiscount = findMaxSubServiceDiscount(subService, orderEntity);
                if (subServiceDiscount != null) {
                    result.put(subService.getId(), subServiceDiscount);
                }
            }
        }

        return result;
    }
}
