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

import com.omniesoft.commerce.owner.controller.user.payload.CustomerPayload;
import com.omniesoft.commerce.owner.controller.user.payload.SearchType;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.account.AccountProfileDataSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 19.10.17
 */
public interface CustomerService {

    Page<AccountProfileDataSummary> findUsersByTypeAndPattern(UUID organizationId, SearchType type, String searchPattern,
                                                              UserEntity userEntity);

    Page<AccountProfileDataSummary> findByEmail(String email);

    Page<AccountProfileDataSummary> findByCardId(String card);

    Page<AccountProfileDataSummary> findByPhone(String phone);

    AccountProfileDataSummary findCustomerInformation(UUID customerId, UUID organizationId, UserEntity userEntity);

    Page<CustomerPayload> findCustomersByOrganizationId(UUID organizationId, LocalDateTime from, LocalDateTime to,
                                                        UserEntity userEntity, Pageable pageable);

    Page<CustomerPayload> findCustomersByOrganizationIdAndServiceId(UUID organizationId, UUID serviceId, LocalDateTime from,
                                                                    LocalDateTime to,
                                                                    UserEntity userEntity, Pageable pageable);

    Page<CustomerPayload> findCustomersByOrganizationIdAndGroupId(UUID organizationId, UUID groupId, LocalDateTime from,
                                                                  LocalDateTime to,
                                                                  UserEntity userEntity, Pageable pageable);


}
