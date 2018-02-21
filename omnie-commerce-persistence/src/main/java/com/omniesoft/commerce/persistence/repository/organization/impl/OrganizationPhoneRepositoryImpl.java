package com.omniesoft.commerce.persistence.repository.organization.impl;

import com.omniesoft.commerce.persistence.repository.organization.OrganizationPhoneRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 09.10.17
 */
@Transactional
public class OrganizationPhoneRepositoryImpl implements OrganizationPhoneRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void delete(UUID organizationId, UUID phoneId) {
        em.createQuery(
                "delete from OrganizationPhoneEntity p  " +
                        " where p.id = :id" +
                        " and  p.organization.id = :organizationId"
        )
                .setParameter("id", phoneId)
                .setParameter("organizationId", organizationId)
                .executeUpdate();
    }
}
