package com.omniesoft.commerce.persistence.repository.admin;

import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.projection.admin.AdminRoleSummary;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.06.17
 */
@Transactional
public interface AdminRoleRepository extends CrudRepository<AdminRoleEntity, UUID>, AdminRoleRepositoryCustom {

    Set<AdminRoleEntity> findByOrganizationId(UUID organizationId);

    AdminRoleEntity findAllByIdAndOrganizationId(UUID roleId, UUID organizationId);

    List<AdminRoleSummary> findAllByOrganizationId(UUID organizationId);
}
