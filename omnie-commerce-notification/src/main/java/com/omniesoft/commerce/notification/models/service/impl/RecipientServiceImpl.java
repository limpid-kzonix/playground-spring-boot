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

package com.omniesoft.commerce.notification.models.service.impl;

import com.omniesoft.commerce.notification.models.service.RecipientService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.repository.admin.AdminRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.omniesoft.commerce.notification.models.service.impl.StreamEnhancer.distinctByKey;

@Service
@AllArgsConstructor
public class RecipientServiceImpl implements RecipientService {


    private AdminRoleRepository adminRoleRepository;

    @Override
    public List<UserEntity> findRecipientByOrganizationId(UUID org) {

        Set<AdminRoleEntity> byOrganizationId = adminRoleRepository
                .findByOrganizationId(org);

        return byOrganizationId.stream()
                .flatMap(adminRoleEntity -> adminRoleEntity.getAdmins().stream())
                .filter
                        (distinctByKey(UserEntity::getId))
                .collect(Collectors.toList());
    }
}
