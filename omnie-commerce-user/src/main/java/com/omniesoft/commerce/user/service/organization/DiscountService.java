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

package com.omniesoft.commerce.user.service.organization;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.user.controller.organization.payload.OrganizationDiscountPayload;
import com.omniesoft.commerce.user.controller.organization.payload.ServiceDiscountPayload;

import java.util.Map;
import java.util.UUID;

public interface DiscountService {

    OrganizationDiscountPayload findOrganizationDiscounts(UUID organizationId, UserEntity userEntity);

    ServiceDiscountPayload findServiceDiscounts(UUID organizationId, UUID serviceId, UserEntity userEntity);

    DiscountEntity findMaxServiceDiscount(OrderEntity orderEntity);

    DiscountEntity findMaxSubServiceDiscount(OrderSubServicesEntity subService, OrderEntity orderEntity);

    Map<UUID, DiscountEntity> findMaxSubServicesDiscounts(OrderEntity orderEntity);

}
