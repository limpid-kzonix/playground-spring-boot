package com.omniesoft.commerce.persistence.repository.service.impl;

import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import com.omniesoft.commerce.persistence.repository.service.SubServiceRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 25.10.17
 */
@Transactional
public class SubServiceRepositoryImpl implements SubServiceRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public SubServiceEntity findByIdAndOrganizationId(UUID id, UUID organizationId) {

        return em.createQuery("select ss" +
                        " from SubServiceEntity ss" +
                        " left join ss.service s " +
                        " left join s.organization o   " +
                        " where ss.id= :id" +
                        " and o.id = :organizationId",
                SubServiceEntity.class)
                .setParameter("id", id)
                .setParameter("organizationId", organizationId)
                .getSingleResult();
    }

    @Override
    public SubServiceEntity findByIdAndServiceId(UUID id, UUID serviceId) {

        return em.createQuery("select ss" +
                        " from SubServiceEntity ss" +
                        " where ss.id=:id" +
                        " and ss.service.id = :serviceId",
                SubServiceEntity.class)
                .setParameter("id", id)
                .setParameter("serviceId", serviceId)
                .getSingleResult();
    }

    @Override
    public void changeDeleteStatus(UUID subServiceId, Boolean deleteStatus) {

        em.createQuery(
                "update " +
                        "SubServiceEntity ss " +
                        "set ss.deleteStatus = :deleteStatus " +
                        "where ss.id = :subServiceId")
                .setParameter("subServiceId", subServiceId)
                .setParameter("deleteStatus", deleteStatus)
                .executeUpdate();
    }

    /*
     * Use only for owner and user module!
     * */
    @Override
    public List<SubServiceEntity> findByIdAndOrganizationIdWithPrice(UUID id, UUID organizationId) {

        return em.createQuery("" +
                "   select ss " +
                "   from SubServiceEntity ss " +
                "   left join fetch ss.prices ssp " +
                "   where ss.service.id = :id " +
                "   and ss.service.organization.id = :organizationId" +
                "   and ss.deleteStatus = false ", SubServiceEntity.class)
                .setParameter("id", id)
                .setParameter("organizationId", organizationId)
                .getResultList();
    }

    @Override
    public List<SubServiceEntity> findByIdAndOrganizationIdWithPriceAndDeleteStatusIsTrue(UUID id, UUID organizationId) {

        return em.createQuery("" +
                "   select ss " +
                "   from SubServiceEntity ss " +
                "   left join fetch ss.prices ssp " +
                "   where ss.service.id = :id " +
                "   and ss.service.organization.id = :organizationId" +
                "   and ss.deleteStatus = false ", SubServiceEntity.class)
                .setParameter("id", id)
                .setParameter("organizationId", organizationId)
                .getResultList();
    }
}
