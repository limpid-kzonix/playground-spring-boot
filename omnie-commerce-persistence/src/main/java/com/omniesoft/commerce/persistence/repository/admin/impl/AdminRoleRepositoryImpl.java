package com.omniesoft.commerce.persistence.repository.admin.impl;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.repository.admin.AdminRoleRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.10.17
 */
@Transactional
public class AdminRoleRepositoryImpl implements AdminRoleRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void delete(UUID organizationId, UUID roleId) {
        em.createQuery(
                "delete from AdminRoleEntity r" +
                        " where r.id = :id" +
                        " and r.organization.id = :organizationId")
                .setParameter("id", roleId)
                .setParameter("organizationId", organizationId)
                .executeUpdate();
    }

    @Override
    public Set<AdminRoleEntity> findByRolesOrganizationId(UUID organizationId) {
        List<AdminRoleEntity> list = em.createQuery(
                "select r from AdminRoleEntity r" +
                        " left join fetch r.permissions" +
                        " where r.organization.id = :organizationId",
                AdminRoleEntity.class)
                .setParameter("organizationId", organizationId)
                .getResultList();

        return new HashSet<>(list);
    }

    @Override
    public Set<UserEntity> findAdminsByOrganizationIdFetchOauth(UUID organizationId) {
        List<UserEntity> list = em.createQuery(
                "select u" +
                        " from UserEntity u" +
                        " left join u.roles r" +
                        " join fetch u.oAuth" +
                        " where r.organization.id = :organizationId" +
                        " group by u.id",
                UserEntity.class)
                .setParameter("organizationId", organizationId)
                .getResultList();

        return new HashSet<>(list);
    }

    @Override
    public Set<AdminRoleEntity> findByOrganizationIdWithPermissionsAndAdmins(UUID organizationId) {
        List<AdminRoleEntity> roles = em.createQuery(
                "select r from AdminRoleEntity r" +
                        " left join r.permissions" +
                        " left join r.admins" +
                        " where r.organization.id = :organizationId",
                AdminRoleEntity.class)
                .setParameter("organizationId", organizationId)
                .getResultList();
        return new HashSet<>(roles);
    }

    @Override
    public AdminRoleEntity findByOrganizationAndUser(OrganizationEntity org, UserEntity admin) {
        return em.createQuery(
                "select r from AdminRoleEntity r" +
                        " left join r.permissions" +
                        " where r.organization.id = :organizationId" +
                        " and r.admins.id = :userId",
                AdminRoleEntity.class)
                .setParameter("organizationId", org.getId())
                .setParameter("userId", admin.getId())
                .getSingleResult();
    }

    @Override
    public void updateRoleName(UUID organizationId, UUID roleId, String name) {
        em.createQuery(
                "update AdminRoleEntity r" +
                        " set r.name =:name" +
                        " where r.id = :id" +
                        " and r.organization.id = :organizationId")
                .setParameter("id", roleId)
                .setParameter("organizationId", organizationId)
                .setParameter("name", name)
                .executeUpdate();

    }
}
