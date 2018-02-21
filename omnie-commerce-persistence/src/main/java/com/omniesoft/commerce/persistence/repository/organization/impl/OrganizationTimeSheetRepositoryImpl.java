package com.omniesoft.commerce.persistence.repository.organization.impl;

import com.omniesoft.commerce.persistence.repository.organization.OrganizationTimeSheetRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 10.10.17
 */
@Transactional
public class OrganizationTimeSheetRepositoryImpl implements OrganizationTimeSheetRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void delete(UUID organizationId, UUID timesheetId) {
        em.createQuery(
                "delete from OrganizationTimeSheetEntity t  " +
                        " where t.id = :id" +
                        " and t.organization.id = :organizationId"
        )
                .setParameter("id", timesheetId)
                .setParameter("organizationId", organizationId)
                .executeUpdate();
    }
}
