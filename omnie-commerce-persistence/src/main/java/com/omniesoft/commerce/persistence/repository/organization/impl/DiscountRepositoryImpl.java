package com.omniesoft.commerce.persistence.repository.organization.impl;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.repository.organization.DiscountRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 24.10.17
 */
@Transactional
public class DiscountRepositoryImpl implements DiscountRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void deleteByIdAndOrganizationId(UUID discountId, UUID organizationId) {

        em.createQuery(
                "delete from DiscountEntity d  " +
                        " where d.id = :id" +
                        " and  d.organization.id = :organizationId")
                .setParameter("id", discountId)
                .setParameter("organizationId", organizationId)
                .executeUpdate();
    }

    @Override
    public DiscountEntity findFullDiscountData(UUID discountId, UUID organizationId) {

        return em.createQuery("select d" +
                        " from DiscountEntity d" +
                        " left join fetch d.associatedServices" +
                        " left join fetch d.associatedUsers" +
                        " left join fetch d.associatedSubServices" +
                        " join d.organization org" +
                        " where d.id = :id" +
                        " and org.id= :organizationId",
                DiscountEntity.class)
                .setParameter("id", discountId)
                .setParameter("organizationId", organizationId)
                .getSingleResult();
    }

    @Override
    public DiscountEntity findByIdAndOrganizationIdWithServices(UUID discountId, UUID organizationId) {

        return em.createQuery("select d" +
                        " from DiscountEntity d" +
                        " left join fetch d.associatedServices" +
                        " join d.organization org" +
                        " where d.id = :id" +
                        " and org.id= :organizationId",
                DiscountEntity.class)
                .setParameter("id", discountId)
                .setParameter("organizationId", organizationId)
                .getSingleResult();
    }

    @Override
    public DiscountEntity findByIdAndOrganizationIdWithSubServices(UUID discountId, UUID organizationId) {

        return em.createQuery("select d" +
                        " from DiscountEntity d" +
                        " left join fetch d.associatedSubServices" +
                        " join d.organization org" +
                        " where d.id = :id" +
                        " and org.id= :organizationId",
                DiscountEntity.class)
                .setParameter("id", discountId)
                .setParameter("organizationId", organizationId)
                .getSingleResult();
    }

    @Override
    public DiscountEntity findByIdAndOrganizationIdWithUsers(UUID discountId, UUID organizationId) {

        return em.createQuery("select d" +
                        " from DiscountEntity d" +
                        " left join fetch d.associatedUsers" +
                        " join d.organization org" +
                        " where d.id = :id" +
                        " and org.id= :organizationId",
                DiscountEntity.class)
                .setParameter("id", discountId)
                .setParameter("organizationId", organizationId)
                .getSingleResult();
    }

    @Override
    public List<DiscountEntity> findFullDiscountsDataByOrganizationId(UUID organizationId) {

        return em.createQuery("" +
                        " select d from DiscountEntity d " +
                        "   left join d.organization org " +
                        "   left join fetch d.associatedUsers users " +
                        "   left join fetch d.associatedServices services " +
                        "   left join fetch d.associatedSubServices subServices " +
                        " where org.id = :organizationId " +
                        "   and :currentTime between d.startTime and d.endTime",
                DiscountEntity.class)
                .setParameter("organizationId", organizationId)
                .setParameter("currentTime", LocalDateTime.now())
                .getResultList();
    }


    @Override
    public List<DiscountEntity> findByOrganizationIdForUser(UUID organizationId, UserEntity userEntity) {

        return em.createQuery("" +
                        "select d from DiscountEntity d " +
                        "left join d.organization org " +
                        "left join d.associatedUsers users " +
                        "where org.id = :organizationId " +
                        "and :userEntity in (users.id) " +
                        "and :currentTime between d.startTime and d.endTime",
                DiscountEntity.class)
                .setParameter("organizationId", organizationId)
                .setParameter("userEntity", userEntity.getId())
                .setParameter("currentTime", LocalDateTime.now())
                .getResultList();
    }

    @Override
    public List<DiscountEntity> findFullDiscountDataByServiceId(UUID organizationId, UUID serviceId,
                                                                UserEntity userEntity) {

        return em.createQuery("" +
                        "select d from DiscountEntity d " +
                        "left join d.organization org " +
                        "left join d.associatedUsers users " +
                        "left join d.associatedServices services " +
                        "left join fetch d.associatedSubServices subServices " +
                        "where :serviceId in (services.id) " +
                        "and subServices.service.id = :serviceId " +
                        "and  org.id = :organizationId  " +
                        "and :currentTime between d.startTime and d.endTime",
                DiscountEntity.class)
                .setParameter("organizationId", organizationId)
                .setParameter("serviceId", serviceId)
                .setParameter("currentTime", LocalDateTime.now())
                .getResultList();
    }

    @Override
    public List<DiscountEntity> findNotPersonalByServiceAndActiveDate(UUID serviceId, LocalDateTime start) {
        return em.createQuery("select d" +
                        " from DiscountEntity d" +
                        " inner join d.associatedServices services" +
                        " where services.id = :id " +
                        " and d.startTime < :time" +
                        " and d.endTime > :time" +
                        " and d.personalStatus = false",
                DiscountEntity.class)
                .setParameter("id", serviceId)
                .setParameter("time", start)
                .getResultList();
    }

    @Override
    public List<DiscountEntity> findPersonalByUserAndServiceAndActiveDate(UUID userId, UUID serviceId,
                                                                          LocalDateTime start) {
        return em.createQuery("select d" +
                        " from DiscountEntity d" +
                        " inner join d.associatedUsers users" +
                        " inner join d.associatedServices services" +
                        " where users.id = :userId" +
                        " and services.id = :serviceId" +
                        " and d.startTime < :time" +
                        " and d.endTime > :time" +
                        " and d.personalStatus = true",
                DiscountEntity.class)
                .setParameter("userId", userId)
                .setParameter("serviceId", serviceId)
                .setParameter("time", start)
                .getResultList();
    }

    @Override
    public List<DiscountEntity> findNotPersonalBySubServiceAndActiveDate(UUID subServiceId, LocalDateTime start) {
        return em.createQuery("select d" +
                        " from DiscountEntity d" +
                        " inner join d.associatedSubServices subServices" +
                        " where subServices.id = :id " +
                        " and d.startTime < :time" +
                        " and d.endTime > :time" +
                        " and d.personalStatus = false",
                DiscountEntity.class)
                .setParameter("id", subServiceId)
                .setParameter("time", start)
                .getResultList();
    }

    @Override
    public List<DiscountEntity> findPersonalByUserAndSubServiceAndActiveDate(UUID userId, UUID subServiceId, LocalDateTime start) {
        return em.createQuery("select d" +
                        " from DiscountEntity d" +
                        " inner join d.associatedUsers users" +
                        " inner join d.associatedSubServices subServices" +
                        " where users.id = :userId" +
                        " and subServices.id = :subServiceId" +
                        " and d.startTime < :time" +
                        " and d.endTime > :time" +
                        " and d.personalStatus = true",
                DiscountEntity.class)
                .setParameter("userId", userId)
                .setParameter("subServiceId", subServiceId)
                .setParameter("time", start)
                .getResultList();
    }
}
