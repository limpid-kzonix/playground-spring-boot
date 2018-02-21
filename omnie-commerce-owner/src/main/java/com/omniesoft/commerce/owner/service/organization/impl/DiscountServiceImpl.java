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

package com.omniesoft.commerce.owner.service.organization.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.owner.controller.organization.payload.DiscountDto;
import com.omniesoft.commerce.owner.controller.organization.payload.SaveDiscountDto;
import com.omniesoft.commerce.owner.converter.DiscountConverter;
import com.omniesoft.commerce.owner.service.organization.DiscountService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServicePriceEntity;
import com.omniesoft.commerce.persistence.projection.organization.DiscountSummary;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.organization.DiscountRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceTimingRepository;
import com.omniesoft.commerce.persistence.repository.service.SubServicePriceRepository;
import com.omniesoft.commerce.persistence.repository.service.SubServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes.DISCOUNT_PERSONAL_STATUS_VIOLATION;
import static com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes.DISCOUNT_VIOLATION_LIMITS;

/**
 * @author Vitalii Martynovskyi
 * @since 23.10.17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    private final DiscountConverter discountConverter;

    private final OrganizationRepository organizationRepository;

    private final ServiceRepository serviceRepository;

    private final ServiceTimingRepository serviceTimingRepository;

    private final SubServiceRepository subServiceRepository;

    private final SubServicePriceRepository subServicePriceRepository;

    private final UserRepository userRepository;


    @Override
    public List<DiscountSummary> findByOrganizationId(UUID organizationId) {

        return discountRepository.findByOrganizationId(organizationId);
    }

    @Override
    public UUID save(UUID organizationId, SaveDiscountDto discount, UserEntity user) {

        OrganizationEntity organization = organizationRepository.findOne(organizationId);

        DiscountEntity discountEntity = new DiscountEntity();
        discountEntity.setName(discount.getName());
        discountEntity.setPercent(discount.getPercent());
        discountEntity.setPersonalStatus(discount.getPersonalStatus());
        discountEntity.setStartTime(discount.getStartTime().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.now().minus(2, ChronoUnit.MINUTES))) ? LocalDateTime.now() : discount.getStartTime());
        discountEntity.setEndTime(discount.getEndTime());
        discountEntity.setUpdateByUser(user);
        discountEntity.setOrganization(organization);


        discountRepository.save(discountEntity);
        return discountEntity.getId();
    }


    @Override
    public void delete(UUID discountId, UUID organizationId) {

        discountRepository.deleteByIdAndOrganizationId(discountId, organizationId);
    }

    @Override
    public DiscountDto findById(UUID discountId, UUID organizationId) {

        DiscountEntity discount = discountRepository.findFullDiscountData(discountId, organizationId);
        return discountConverter.discountDto(discount);
    }

    @Override
    public void addService(UUID discountId, UUID organizationId, UUID serviceId) {

        DiscountEntity discount = discountRepository
                .findByIdAndOrganizationIdWithServices(discountId, organizationId);
        ServiceEntity service = serviceRepository.findByIdAndOrganizationId(serviceId,
                organizationId);

        ServiceTimingEntity serviceTiming = serviceTimingRepository
                .findByServiceId(serviceId, discount.getStartTime());
        if (serviceTiming == null) {
            throw new UsefulException("Invalid discount 'startTime', available service-setting`s 'activeFrom' datetime is after discount`s 'startTime' datetime. It is meaning that you want put discount 'startTime' less than service 'createTime", OwnerModuleErrorCodes.DISCOUNT_DATE_CONFIGURATION_INCORRECT);
        }

        if (discount.getPercent() <= serviceTiming.getMaxDiscount()) {
            discount.getAssociatedServices().add(service);
            discountRepository.save(discount);
        } else {
            log.debug("Discount violates service settings: " + serviceId);
            throw new UsefulException("Max discount: " + serviceTiming.getMaxDiscount(),
                    DISCOUNT_VIOLATION_LIMITS);
        }

    }

    @Override
    public void deleteService(UUID discountId, UUID organizationId, UUID serviceId) {

        DiscountEntity discount = discountRepository
                .findByIdAndOrganizationIdWithServices(discountId, organizationId);
        discount.getAssociatedServices().removeIf(s -> s.getId().equals(serviceId));
        discountRepository.save(discount);
    }

    @Override
    public void addSubService(UUID discountId, UUID organizationId, UUID subServiceId) {

        DiscountEntity discount = discountRepository
                .findByIdAndOrganizationIdWithSubServices(discountId, organizationId);

        SubServiceEntity subService = subServiceRepository.findByIdAndOrganizationId(subServiceId, organizationId);

        SubServicePriceEntity subServicePrice = subServicePriceRepository
                .findBySubServiceIdAndActiveFrom(subServiceId, discount.getStartTime());
        if (subServicePrice == null) {
            throw new UsefulException("Invalid discount 'startTime'. Available sub-service-setting`s 'activeFrom' datetime is after discount`s 'startTime' datetime. It is meaning that you want put discount 'startTime' less than sub-service 'createTime", OwnerModuleErrorCodes.DISCOUNT_DATE_CONFIGURATION_INCORRECT);
        }

        if (discount.getPercent() <= subServicePrice.getMaxDiscount()) {
            discount.getAssociatedSubServices().add(subService);
            discountRepository.save(discount);
        } else {
            log.debug("Discount violates subService settings: " + subServiceId);
            throw new UsefulException("Max discount: " + subServicePrice.getMaxDiscount(), DISCOUNT_VIOLATION_LIMITS);
        }


    }

    @Override
    public void deleteSubService(UUID discountId, UUID organizationId, UUID subServiceId) {

        DiscountEntity discount = discountRepository
                .findByIdAndOrganizationIdWithSubServices(discountId, organizationId);

        discount.getAssociatedSubServices().removeIf(ss -> ss.getId().equals(subServiceId));
        discountRepository.save(discount);
    }


    @Override
    public void addUser(UUID discountId, UUID organizationId, UUID userId) {

        DiscountEntity discount = discountRepository.findByIdAndOrganizationIdWithUsers(discountId, organizationId);
        if (discount.getPersonalStatus()) {
            UserEntity user = userRepository.findOne(userId);

            discount.getAssociatedUsers().add(user);
            discountRepository.save(discount);
        } else {
            throw new UsefulException("Discount not personal", DISCOUNT_PERSONAL_STATUS_VIOLATION);
        }
    }

    @Override
    public void deleteUser(UUID discountId, UUID organizationId, UUID userId) {

        DiscountEntity discount = discountRepository.findByIdAndOrganizationIdWithUsers(discountId, organizationId);

        discount.getAssociatedUsers().removeIf(ss -> ss.getId().equals(userId));
        discountRepository.save(discount);
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
