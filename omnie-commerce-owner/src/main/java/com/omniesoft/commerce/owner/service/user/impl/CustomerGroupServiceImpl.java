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

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.owner.controller.user.payload.CustomerGroupPayload;
import com.omniesoft.commerce.owner.controller.user.payload.GroupActionType;
import com.omniesoft.commerce.owner.service.user.CustomerGroupService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.CustomerGroupEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.projection.organization.CustomerGroupShortSummary;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.organization.CustomerGroupRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerGroupServiceImpl implements CustomerGroupService {

    private final CustomerGroupRepository customerGroupRepository;

    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;


    @Override
    public Page<CustomerGroupShortSummary> findByOrganizationId(UUID organizationId, UserEntity userEntity, Pageable
            pageable) {

        return customerGroupRepository.findAllByOrganizationId(organizationId, pageable);
    }

    @Override
    public CustomerGroupPayload createCustomerGroup(UUID organizationId, String groupName, UserEntity userEntity) {

        OrganizationEntity one = getOrganizationEntity(organizationId);

        CustomerGroupEntity save = saveCustomerGroup(groupName, userEntity, one);
        return new CustomerGroupPayload(save.getId(), save.getName());
    }


    @Override
    public void deleteCustomerGroup(UUID organizationId, UUID customerGroupId, UserEntity userEntity) {

        OrganizationEntity one = getOrganizationEntity(organizationId);
        customerGroupRepository.deleteByOrganizationIdAndId(one.getId(), customerGroupId);
    }

    @Override
    public void connectCustomersToGroup(UUID organizationId, UUID customerGroupId, List<UUID> users,
                                        UserEntity userEntity) {
        List<UserEntity> allById = userRepository.findAllById(users);
        CustomerGroupEntity customerGroupEntity = customerGroupRepository
                .findByIdAndOrganizationId(customerGroupId, organizationId);
        customerGroupEntity.setUsers(allById);
        customerGroupRepository.save(customerGroupEntity);

    }

    @Override
    public void deleteCustomersFromGroup(UUID organizationId, UUID customerGroupId, List<UUID> users,
                                         UserEntity userEntity) {

        List<UserEntity> allById = userRepository.findAllById(users);
        CustomerGroupEntity customerGroupEntity = customerGroupRepository
                .findByIdAndOrganizationId(customerGroupId, organizationId);
        List<UserEntity> userEntities = customerGroupEntity.getUsers();
        if (userEntities != null && !userEntities.isEmpty()) {
            userEntities.removeAll(allById);
        }
        customerGroupEntity.setUsers(userEntities);
        customerGroupRepository.save(customerGroupEntity);
    }

    @Override
    public void actionWithCustomerList(UUID organizationId, UUID customerGroupId, List<UUID> users,
                                       GroupActionType actionType, UserEntity userEntity) {

        switch (actionType) {
            case ADD:
                connectCustomersToGroup(organizationId, customerGroupId, users, userEntity);
                break;
            case DELETE:
                deleteCustomersFromGroup(organizationId, customerGroupId, users, userEntity);
                break;
            default:
                throw new UsefulException("You don`t choose action type", OwnerModuleErrorCodes.ACTION_NOT_ALLOWED);
        }

    }

    private CustomerGroupEntity saveCustomerGroup(String groupName, UserEntity userEntity, OrganizationEntity one) {

        CustomerGroupEntity customerGroupEntity = prepareCustomerGroupEntity(groupName, userEntity, one);
        return customerGroupRepository.save(customerGroupEntity);
    }

    private CustomerGroupEntity prepareCustomerGroupEntity(String groupName, UserEntity userEntity,
                                                           OrganizationEntity one) {

        CustomerGroupEntity customerGroupEntity = new CustomerGroupEntity();
        customerGroupEntity.setCreateby(userEntity);
        customerGroupEntity.setCreateTime(LocalDateTime.now());
        customerGroupEntity.setName(groupName);
        customerGroupEntity.setOrganization(one);
        return customerGroupEntity;
    }

    private OrganizationEntity getOrganizationEntity(UUID organizationId) {

        OrganizationEntity one = organizationRepository.findOne(organizationId);
        if (one == null) {
            throw new UsefulException(organizationId.toString(),
                    InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return one;
    }
}
