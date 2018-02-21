package com.omniesoft.commerce.persistence.repository.account.impl;

import com.omniesoft.commerce.persistence.entity.account.OwnerEntity;
import com.omniesoft.commerce.persistence.repository.account.OwnerRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.10.17
 */
@Transactional
public class OwnerRepositoryImpl implements OwnerRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public OwnerEntity findByOrganizationId(UUID organizationId) {
        return em.createQuery("select owner" +
                        " from OrganizationEntity org" +
                        " inner join org.owner owner" +
                        " where org.id=:id",
                OwnerEntity.class)
                .setParameter("id", organizationId)
                .getSingleResult();
    }

    @Override
    public OwnerEntity findByRoleId(UUID roleId) {
        return em.createQuery("select owner" +
                        " from AdminRoleEntity admin" +
                        " inner join admin.organization org" +
                        " inner join org.owner owner" +
                        " where admin.id=:id",
                OwnerEntity.class)
                .setParameter("id", roleId)
                .getSingleResult();
    }

    @Override
    public OwnerEntity findByDiscountId(UUID discountId) {
        return em.createQuery("select owner" +
                        " from DiscountEntity discount" +
                        " inner join discount.organization org" +
                        " inner join org.owner owner" +
                        " where discount.id=:id",
                OwnerEntity.class)
                .setParameter("id", discountId)
                .getSingleResult();

    }
}
