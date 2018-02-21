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
import com.omniesoft.commerce.persistence.projection.admin.AdminRoleSummary;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */

public interface PersonnelManagementService {
    UUID saveRole(UUID organizationId, String roleName, UserEntity user);

    List<AdminRoleSummary> getRoles(UUID organizationId);

    void deleteRole(UUID organizationId, UUID roleId);

    void updateRoleName(UUID organizationId, UUID roleId, String name);

    UUID addPermissionToRole(UUID organizationId, UUID roleId, AdminPermission permission);

    void deleteRolePermission(UUID organizationId, UUID roleId, UUID permissionId);

    void deleteUserFromRole(UUID organizationId, UUID roleId, UUID userId);

    void addUserToRole(UUID organizationId, UUID roleId, UUID userId);

    void addAdminToService(UUID organizationId, UUID userId, UUID serviceId);

    void deleteAdminFromService(UUID organizationId, UUID userId, UUID serviceId);

}
