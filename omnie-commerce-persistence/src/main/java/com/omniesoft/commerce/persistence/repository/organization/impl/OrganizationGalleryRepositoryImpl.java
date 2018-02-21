package com.omniesoft.commerce.persistence.repository.organization.impl;

import com.omniesoft.commerce.persistence.repository.organization.OrganizationGalleryRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 05.10.17
 */
public class OrganizationGalleryRepositoryImpl implements OrganizationGalleryRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void removeImages(UUID imageId, UUID organizationId) {

        em.createQuery(
                "delete from OrganizationGalleryEntity g  " +
                        " where g.id = :id" +
                        " and  g.organization.id = :organizationId")
                .setParameter("id", imageId)
                .setParameter("organizationId", organizationId)
                .executeUpdate();
    }
}
