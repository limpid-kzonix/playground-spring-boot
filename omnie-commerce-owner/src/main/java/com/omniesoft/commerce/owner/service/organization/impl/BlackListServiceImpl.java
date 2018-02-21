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

import com.omniesoft.commerce.owner.controller.organization.payload.blacklist.BlackListPayload;
import com.omniesoft.commerce.owner.service.organization.BlackListService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.BlackListEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.projection.organization.BlackListSummary;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.persistence.repository.organization.BlackListRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BlackListServiceImpl implements BlackListService {

    private final BlackListRepository blackListRepository;

    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    private final ModelMapper modelMapper;

    @Override
    public BlackListPayload addToBlackList(String comment, UUID userId, UUID orgId, UserEntity userEntity) {


        OrganizationEntity org = organizationRepository.findOne(orgId);
        UserEntity user = userRepository.findOne(userId);

        BlackListEntity blackListEntity = new BlackListEntity();
        blackListEntity.setOrganization(org);
        blackListEntity.setUser(user);
        blackListEntity.setCreateByUser(userEntity);
        blackListEntity.setComment(comment);

        BlackListEntity save = blackListRepository.save(blackListEntity);
        return modelMapper.map(save, BlackListPayload.class);
    }

    @Override
    public void deleteFromBlackList(UUID userId, UUID orgId, UserEntity userEntity) {

        blackListRepository.deleteByOrganizationIdAndUserId(orgId, userId);
    }

    @Override
    public Page<BlackListSummary> getUsersInBlackList(UUID orgId, String searchPattern, UserEntity userEntity,
                                                      Pageable pageable) {

        return blackListRepository
                .findByUserProfileFirstNameAndOrganizationId(searchPattern, orgId, pageable);

    }


}
