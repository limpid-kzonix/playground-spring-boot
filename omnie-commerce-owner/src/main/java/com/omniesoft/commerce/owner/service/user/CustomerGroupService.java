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

package com.omniesoft.commerce.owner.service.user;

import com.omniesoft.commerce.owner.controller.user.payload.CustomerGroupPayload;
import com.omniesoft.commerce.owner.controller.user.payload.GroupActionType;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.CustomerGroupShortSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerGroupService {

    Page<CustomerGroupShortSummary> findByOrganizationId(UUID organizationId, UserEntity userEntity,
                                                         Pageable pageable);

    CustomerGroupPayload createCustomerGroup(UUID organizationId, String groupName, UserEntity userEntity);

    void deleteCustomerGroup(UUID organizationId, UUID customerGroupId, UserEntity userEntity);

    void connectCustomersToGroup(UUID organizationId, UUID customerGroupId, List<UUID> users, UserEntity userEntity);

    void deleteCustomersFromGroup(UUID organizationId, UUID customerGroupId, List<UUID> users, UserEntity userEntity);

    void actionWithCustomerList(UUID organizationId, UUID customerGroupId, List<UUID> users,
                                GroupActionType actionType, UserEntity userEntity);


}
