package com.omniesoft.commerce.persistence.repository.service.impl;

import com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 22.08.17
 */

public class ServiceRepositoryImpl implements ServiceRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public ServiceEntity findWithOrganizationAndSettings(UUID id) {
        return em.createQuery("select s " +
                        " from ServiceEntity s" +
                        " left join fetch s.organization" +
                        " where s.id = :id"
                , ServiceEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public ServiceRowUserExtendDto findWithUserServiceInfo(UUID serviceId, UUID organizationId, UserEntity userEntity) {
        return em.createQuery(
                "select " +
                        "   new com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto(" +
                        "       service.id," +
                        "       service.name," +
                        "       service.description," +
                        "       service.logoId," +
                        "       service.freezeStatus," +
                        "       service.reason," +
                        "       service.createTime," +
                        "       (case when (select u.id" +
                        "                        from UserEntity u" +
                        "                        join u.favoriteServices fs" +
                        "                        where u =:user and fs.id = :service)" +
                        "        is not null  then true  else false end)," +
                        "       coalesce( mark.rating, 0)," +
                        "       organization.id," +
                        "       organization.name,  " +
                        "       organization.placeId," +
                        "       organization.longitude," +
                        "       organization.latitude" +
                        "   ) " +
                        "from ServiceEntity service" +
                        "   left   join     service.mark mark " +
                        "   left   join     service.organization organization " +
                        "where " +
                        "    service.id = :service" +
                        "    and organization.id = :organization " +
                        "group by" +
                        "       service.id," +
                        "       organization.id," +
                        "       mark.id"
                , ServiceRowUserExtendDto.class)
                .setParameter("service", serviceId)
                .setParameter("organization", organizationId)
                .setParameter("user", userEntity)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void calculateMarks() {
        em.createNativeQuery("SELECT count(1) FROM (SELECT (mark_service(uuid) IS NULL) FROM service) as s2 ").getSingleResult();
    }

    @Override
    @Transactional
    public void setFreezeStatus(UUID serviceId, Boolean status, String reason) {
        em.createQuery("" +
                "update " +
                "   ServiceEntity s " +
                "set " +
                "   s.freezeStatus = :freezeStatus," +
                "   s.reason = :reason " +
                "where " +
                "   s.id = :serviceId")
                .setParameter("serviceId", serviceId)
                .setParameter("freezeStatus", status)
                .setParameter("reason", reason)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void setDeleteStatus(UUID serviceId, Boolean status) {
        em.createQuery("" +
                "update " +
                "   ServiceEntity s " +
                "set " +
                "   s.deleteStatus = :deleteStatus " +
                "where " +
                "   s.id = :serviceId ")
                .setParameter("serviceId", serviceId)
                .setParameter("deleteStatus", status)
                .executeUpdate();
    }


    @Override
    public List<ServiceEntity> getOrganizationPriceList(UUID organizationId) {

        return em.createQuery("" +
                "select distinct s from ServiceEntity s " +
                "left join fetch s.prices as serviceprices " +
                "left join fetch s.subCategories as serviceCategories  " +
                "where serviceprices.activeFrom = " +
                "(select " +
                "   max(sp2.activeFrom) " +
                "from ServicePriceEntity sp2 " +
                "   left join sp2.service as service2 " +
                "where " +
                "   sp2.activeFrom <= :date " +
                "   and service2.id = s.id " +
                "" +
                ") " +
                "" +

                "", ServiceEntity.class)
                .setParameter("date", LocalDateTime.now()).getResultList();
    }
}
