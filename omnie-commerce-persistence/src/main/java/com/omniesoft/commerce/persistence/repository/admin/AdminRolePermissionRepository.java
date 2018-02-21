package com.omniesoft.commerce.persistence.repository.admin;

import com.omniesoft.commerce.persistence.entity.admin.AdminRolePermissionEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 07.07.17
 */
@Transactional
public interface AdminRolePermissionRepository extends CrudRepository<AdminRolePermissionEntity, UUID> {
}
