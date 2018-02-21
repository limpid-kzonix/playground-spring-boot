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

package com.omniesoft.commerce.owner.service.user.impl;

import com.omniesoft.commerce.owner.controller.user.payload.CustomerGroupPayload;
import com.omniesoft.commerce.owner.controller.user.payload.CustomerPayload;
import com.omniesoft.commerce.owner.controller.user.payload.SearchType;
import com.omniesoft.commerce.owner.service.user.CustomerService;
import com.omniesoft.commerce.persistence.dto.organization.CustomerDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.account.AccountProfileDataSummary;
import com.omniesoft.commerce.persistence.projection.organization.CustomerGroupShortSummary;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.organization.CustomerGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Vitalii Martynovskyi
 * @since 19.10.17
 */
@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;

    private final CustomerGroupRepository customerGroupRepository;


    @Override
    public Page<AccountProfileDataSummary> findUsersByTypeAndPattern(UUID organizationId, SearchType type, String searchPattern,
                                                                     UserEntity userEntity) {

        return chooseSearchMethod(type, searchPattern);
    }

    private Page<AccountProfileDataSummary> chooseSearchMethod(SearchType type, String searchPattern) {

        switch (type) {
            case EMAIL:
                return findByEmail(searchPattern);
            case PHONE:
                return findByPhone(searchPattern);
            case CARD_ID:
                return findByCardId(searchPattern);
            default:
                return findByEmail(searchPattern);
        }
    }

    @Override
    public Page<AccountProfileDataSummary> findByEmail(String email) {

        Pageable topFive = new PageRequest(0, 5);
        return userRepository.findByEmailContaining(email, topFive);
    }

    @Override
    public Page<AccountProfileDataSummary> findByCardId(String card) {

        Pageable topFive = new PageRequest(0, 5);
        return userRepository.findByProfileOmnieCardContaining(card, topFive);
    }

    @Override
    public Page<AccountProfileDataSummary> findByPhone(String phone) {

        Pageable topFive = new PageRequest(0, 5);
        return userRepository
                .findByProfilePhoneContaining(
                        phone.trim().replaceAll("[+^0-9]+", ""),
                        topFive);
    }

    @Override
    public AccountProfileDataSummary findCustomerInformation(UUID customerId, UUID organizationId,
                                                             UserEntity userEntity) {

        return userRepository.findById(customerId);
    }

    @Override
    public Page<CustomerPayload> findCustomersByOrganizationId(UUID organizationId, LocalDateTime from,
                                                               LocalDateTime to,
                                                               UserEntity userEntity, Pageable pageable) {

        Page<CustomerDto> customersByOrganizationIdWithGroups = customerGroupRepository
                .findCustomersByOrganizationIdWithGroups(organizationId, from, to, pageable);
        Page<CustomerPayload> mapped = customersByOrganizationIdWithGroups.map(getConverter(organizationId));
        return mapped;
    }


    @Override
    public Page<CustomerPayload> findCustomersByOrganizationIdAndServiceId(UUID organizationId, UUID serviceId,
                                                                           LocalDateTime from, LocalDateTime to,
                                                                           UserEntity userEntity, Pageable pageable) {

        Page<CustomerDto> customersByOrganizationIdAndServiceIdWithGroups = customerGroupRepository
                .findCustomersByOrganizationIdAndServiceIdWithGroups(organizationId, serviceId, from,
                        to, pageable);
        Page<CustomerPayload> mapped = customersByOrganizationIdAndServiceIdWithGroups.map(getConverter(organizationId));
        return mapped;
    }

    @Override
    public Page<CustomerPayload> findCustomersByOrganizationIdAndGroupId(UUID organizationId, UUID groupId,
                                                                         LocalDateTime from,
                                                                         LocalDateTime to, UserEntity userEntity,
                                                                         Pageable pageable) {

        Page<CustomerDto> customersByOrganizationIdAndGroupIdWithGroups = customerGroupRepository
                .findCustomersByOrganizationIdAndGroupIdWithGroups(organizationId, groupId, from, to,
                        pageable);
        Page<CustomerPayload> mapped = customersByOrganizationIdAndGroupIdWithGroups.map(getConverter(organizationId));
        return mapped;
    }


    private Converter<CustomerDto, CustomerPayload> getConverter(UUID organizationId) {
        return (source) -> {
            CustomerPayload customerPayload = new CustomerPayload();
            customerPayload.setCountOfOrder(source.getCountOfOrder());
            customerPayload.setEmail(source.getEmail());
            customerPayload.setFirstName(source.getFirstName());
            customerPayload.setImage(source.getImage());
            customerPayload.setLastName(source.getSurName());
            customerPayload.setLastOrderDateTime(source.getLastOrderDateTime());
            customerPayload.setSumOfOrdersPrice(source.getSumOfOrdersPrice());
            customerPayload.setCustomerGroups(getGroupPayloads(organizationId, source.getId()));
            return customerPayload;
        };
    }

    private List<CustomerGroupPayload> getGroupPayloads(UUID organization, UUID customer) {
        List<CustomerGroupShortSummary> customerGroups = customerGroupRepository.findAllByOrganizationIdAndUsersContains(organization, userEntity(customer));
        return customerGroups.stream()
                .filter(java.util.Objects::nonNull)
                .map(e -> new CustomerGroupPayload(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }

    private UserEntity userEntity(UUID id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        return userEntity;
    }


}
