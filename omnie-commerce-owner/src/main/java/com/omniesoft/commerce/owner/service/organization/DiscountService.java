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

package com.omniesoft.commerce.owner.service.organization;

import com.omniesoft.commerce.owner.controller.organization.payload.DiscountDto;
import com.omniesoft.commerce.owner.controller.organization.payload.SaveDiscountDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.persistence.projection.organization.DiscountSummary;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 23.10.17
 */
public interface DiscountService {
    List<DiscountSummary> findByOrganizationId(UUID organizationId);

    UUID save(UUID organizationId, SaveDiscountDto discount, UserEntity user);

    void delete(UUID discountId, UUID organizationId);

    DiscountDto findById(UUID discountId, UUID organizationId);

    void addService(UUID discountId, UUID organizationId, UUID serviceId);

    void deleteService(UUID discountId, UUID organizationId, UUID serviceId);

    void addSubService(UUID discountId, UUID organizationId, UUID subServiceId);

    void deleteSubService(UUID discountId, UUID organizationId, UUID subServiceId);

    void addUser(UUID discountId, UUID organizationId, UUID userId);

    void deleteUser(UUID discountId, UUID organizationId, UUID userId);

    DiscountEntity findMaxServiceDiscount(OrderEntity orderEntity);

    DiscountEntity findMaxSubServiceDiscount(OrderSubServicesEntity subService, OrderEntity orderEntity);

    Map<UUID, DiscountEntity> findMaxSubServicesDiscounts(OrderEntity orderEntity);
}
