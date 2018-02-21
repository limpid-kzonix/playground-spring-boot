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

package com.omniesoft.commerce.user.controller.organization;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.user.controller.organization.payload.OrganizationDiscountPayload;
import com.omniesoft.commerce.user.controller.organization.payload.ServiceDiscountPayload;
import com.omniesoft.commerce.user.service.organization.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class DiscountController extends AbstractOrganizationController {

    private final DiscountService discountService;

    @GetMapping(path = "/{organization-id}/discounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDiscountPayload getOrganizationDiscounts(
            @PathVariable("organization-id") UUID id,
            @ApiIgnore UserEntity userEntity) {
        return discountService.findOrganizationDiscounts(id, userEntity);
    }

    @GetMapping(path = "/{organization-id}/services/{service-id}/discounts", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public ServiceDiscountPayload getServiceDiscountDiscounts(
            @PathVariable("organization-id") UUID orgId,
            @PathVariable("service-id") UUID serviceId,
            @ApiIgnore UserEntity userEntity) {
        return discountService.findServiceDiscounts(orgId, serviceId, userEntity);
    }

}
