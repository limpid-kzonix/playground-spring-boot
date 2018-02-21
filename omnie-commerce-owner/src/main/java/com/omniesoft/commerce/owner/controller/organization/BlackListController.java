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

package com.omniesoft.commerce.owner.controller.organization;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.organization.payload.blacklist.BlackListPayload;
import com.omniesoft.commerce.owner.service.organization.BlackListService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.organization.BlackListSummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RestController
@AllArgsConstructor
@Api(tags = "Blacklist API", description = "@limpid")
public class BlackListController extends AbstractOrganizationController {

    private final BlackListService blackListService;
    private final OwnerAccessControlService ownerAccessControlService;

    @PostMapping(path = "/{organization-id}/blacklist")
    public BlackListPayload addToBlackList(
            @ApiParam(required = true)
            @NotBlank @RequestParam("comment") String comment,
            @ApiParam(required = true) @RequestParam("user_id") UUID userId,
            @PathVariable("organization-id") UUID organizationId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.BLACK_LIST_MANAGEMENT);
        return blackListService
                .addToBlackList(comment, userId, organizationId,
                        userEntity
                );
    }

    @DeleteMapping(path = "/{organization-id}/blacklist")
    public void deleteFromBlackList(
            @ApiParam(required = true) @RequestParam("user_id") UUID userId,
            @ApiParam(required = true) @PathVariable("organization-id") UUID organizationId,
            @ApiIgnore UserEntity userEntity) {

        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.BLACK_LIST_MANAGEMENT);
        blackListService.deleteFromBlackList(userId, organizationId, userEntity);
    }

    @GetMapping(path = "/{organization-id}/blacklist")
    public Page<BlackListSummary> getBlackListRecords(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiParam(required = true) @PathVariable("organization-id") UUID organizationId,
            @ApiParam(required = true) @RequestParam(defaultValue = "_", required = false, name = "search", value = "search") String searchPattern,
            @ApiIgnore UserEntity userEntity
    ) {

        return blackListService
                .getUsersInBlackList(organizationId, searchPattern, userEntity, pageable);
    }


}
