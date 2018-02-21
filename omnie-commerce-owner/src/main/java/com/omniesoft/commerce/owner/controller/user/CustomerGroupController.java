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

package com.omniesoft.commerce.owner.controller.user;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.user.payload.CustomerConnectRequest;
import com.omniesoft.commerce.owner.controller.user.payload.CustomerGroupPayload;
import com.omniesoft.commerce.owner.controller.user.payload.GroupActionType;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.owner.service.user.CustomerGroupService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.organization.CustomerGroupShortSummary;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Api(value = "Customers Group Controller", tags = "Customers API", description = "@limpid")
@AllArgsConstructor
public class CustomerGroupController extends AbstractOrganizationController {

    private final CustomerGroupService customerGroupService;
    private final OwnerAccessControlService ownerAccessControlService;

    @GetMapping(path = "/{organization-id}/custommers/groups")
    public Page<CustomerGroupShortSummary> fetchOrganizationCustomerGroups(
            @PathVariable("organization-id") UUID organizationId,
            @ApiIgnore UserEntity userEntity,
            @Valid PageableRequest pageableRequest, Pageable pageable
    ) {
        return customerGroupService.findByOrganizationId(organizationId, userEntity, pageable);
    }

    @PostMapping(path = "/{organization-id}/custommers/groups")
    public CustomerGroupPayload createCustomerGroup(
            @PathVariable("organization-id") UUID organizationId,
            @RequestParam("group_name") String groupName,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.CUSTOMER_GROUP_MANAGEMENT);
        return customerGroupService.createCustomerGroup(organizationId, groupName, userEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{organization-id}/customers/groups/{group-id}/")
    public void actionWithUsersAndGroup(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("group-id") UUID groupId,
            @RequestParam("action") GroupActionType actionType,
            @ApiIgnore UserEntity userEntity,
            @Valid @RequestBody CustomerConnectRequest customerConnectRequest
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.CUSTOMER_GROUP_MANAGEMENT);
        customerGroupService.actionWithCustomerList(organizationId, groupId,
                customerConnectRequest.getUsers(), actionType, userEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{organization-id}/custommers/groups/{group-id}")
    public void deleteCustomerGroup(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("group-id") UUID groupId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.CUSTOMER_GROUP_MANAGEMENT);
        customerGroupService.deleteCustomerGroup(organizationId, groupId, userEntity);
    }


}
