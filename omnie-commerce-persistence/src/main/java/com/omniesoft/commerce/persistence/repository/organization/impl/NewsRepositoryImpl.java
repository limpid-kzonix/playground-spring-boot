package com.omniesoft.commerce.persistence.repository.organization.impl;

import com.omniesoft.commerce.persistence.entity.organization.NewsEntity;
import com.omniesoft.commerce.persistence.repository.organization.NewsRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 22.08.17
 */
@Transactional
public class NewsRepositoryImpl implements NewsRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public NewsEntity findFullData(UUID id) {
        return em.createQuery(
                "select n" +
                        " from NewsEntity n  " +
                        " left join fetch n.services" +
                        " where n.id = :id ",
                NewsEntity.class)
                .setParameter("id", id)
                .getSingleResult();

    }

    @Override
    public NewsEntity findWithServicesById(UUID id) {
        return em.createQuery(
                "select n" +
                        " from NewsEntity n  " +
                        " left join fetch n.services" +
                        " where n.id = :id ",
                NewsEntity.class)
                .setParameter("id", id)
                .getSingleResult();

    }
}
