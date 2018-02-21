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

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 09.10.17
 */
public interface OwnerAccessControlService {
    void hasPermissionByOrganization(UserEntity user, UUID organizationId, AdminPermission personnelManagement);

    void hasPermissionByRoleId(UserEntity user, UUID roleId, AdminPermission personnelManagement);

    void hasPermissionByDiscountId(UserEntity user, UUID discountId, AdminPermission discountManagement);

    void hasPermissionByServiceId(UserEntity user, UUID serviceId, AdminPermission discountManagement);

}
