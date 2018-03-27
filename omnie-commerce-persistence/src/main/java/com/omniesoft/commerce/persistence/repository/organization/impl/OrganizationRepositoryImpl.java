package com.omniesoft.commerce.persistence.repository.organization.impl;

import com.omniesoft.commerce.persistence.dto.organization.OrganizationRowExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 11.08.17
 */
@Transactional
public class OrganizationRepositoryImpl implements OrganizationRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public OrganizationEntity findAllProfileById(UUID organizationId) {
        return em.createQuery(
                "select o" +
                        " from OrganizationEntity o  " +
                        " left join fetch o.owner owner" +
                        " left join fetch owner.account account" +
                        " left join fetch account.profile" +
                        " where o.id = :id",
                OrganizationEntity.class)
                .setParameter("id", organizationId)
                .getSingleResult();
    }

    @Override
    @Transactional
    public OrganizationRowExtendDto findWithUserOrganizationInfo(UUID organizationId, UserEntity userEntity) {
        return em.createQuery("" +
                "select " +
                "   new com.omniesoft.commerce.persistence.dto.organization.OrganizationRowExtendDto(" +
                "       org.id," +
                "       org.name," +
                "       org.email," +
                "       org.logoId," +
                "       org.freezeStatus," +
                "       org.createTime," +
                "       (case when :user in inUsers then true  else false end)," +
                "       coalesce( mark.rating, 0), " +
                "       org.title, " +
                "       org.description," +
                "       org.reason," +
                "       org.placeId," +
                "       org.longitude," +
                "       org.latitude" +
                "   )" +
                "from OrganizationEntity org " +
                "   left    join            org.mark mark" +
                "   left    join            org.inUsersFavorites inUsers " +
                "where " +
                " org.id = :organization " +
                " and inUsers =:user or inUsers is null " +
                "group by" +
                "       org.id," +
                "       inUsers.id," +
                "       mark.rating " +
                "", OrganizationRowExtendDto.class)
                .setParameter("organization", organizationId)
                .setParameter("user", userEntity)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void calculateMarks() {
        em.createNativeQuery("SELECT count(1) FROM (SELECT (mark_organization(uuid) IS NULL) FROM organization) as o2 ").getSingleResult();
    }

    @Override
    public void setFreezeStatus(UUID organizationId, boolean status, String reason) {
        em.createQuery(
                "update OrganizationEntity" +
                        " set freezeStatus = :status," +
                        "     reason = :reason" +
                        " where id=:id")
                .setParameter("id", organizationId)
                .setParameter("status", status)
                .setParameter("reason", reason)
                .executeUpdate();

    }

    @Override
    public void setDeleteStatus(UUID organizationId, boolean status, String reason) {
        em.createQuery(
                "update OrganizationEntity" +
                        " set deleteStatus = :status," +
                        "     reason = :reason" +
                        " where id=:id")
                .setParameter("id", organizationId)
                .setParameter("status", status)
                .setParameter("reason", reason)
                .executeUpdate();

    }

    @Override
    public void updateLocation(UUID organizationId, String placeId, Double longitude, Double latitude) {
        em.createQuery(
                "update OrganizationEntity" +
                        " set placeId = :placeId," +
                        " longitude = :longitude," +
                        " latitude = :latitude" +
                        " where id=:id")
                .setParameter("id", organizationId)
                .setParameter("placeId", placeId)
                .setParameter("longitude", longitude)
                .setParameter("latitude", latitude)
                .executeUpdate();
    }

    @Override
    public void updateBackgroundImage(UUID organizationId, String imageId) {
        em.createQuery(
                "update OrganizationEntity" +
                        " set backgroundImageId = :backgroundImageId" +
                        " where id=:id")
                .setParameter("id", organizationId)
                .setParameter("backgroundImageId", imageId)
                .executeUpdate();
    }

    @Override
    public void updateLogoImageId(UUID organizationId, String imageId) {
        em.createQuery(
                "update OrganizationEntity" +
                        " set logoId = :logoId" +
                        " where id=:id")
                .setParameter("id", organizationId)
                .setParameter("logoId", imageId)
                .executeUpdate();
    }

    @Override
    public void updateDescription(UUID organizationId, String title, String description) {
        em.createQuery(
                "update OrganizationEntity" +
                        " set title = :title," +
                        " description = :description" +
                        " where id=:id")
                .setParameter("id", organizationId)
                .setParameter("title", title)
                .setParameter("description", description)
                .executeUpdate();
    }

    @Override
    public OrganizationEntity findByRoleId(UUID roleId) {
        return em.createQuery(
                "select org" +
                        " from AdminRoleEntity role  " +
                        " inner join role.organization org" +
                        " where role.id = :id",
                OrganizationEntity.class)
                .setParameter("id", roleId)
                .getSingleResult();
    }

    @Override
    public List<OrganizationEntity> findByOwnerOrAdmin(UserEntity user) {
        return em.createQuery(
                "select org" +
                        " from OrganizationEntity org" +
                        " left join org.roles roles" +
                        " left join roles.admins admins" +
                        " inner join org.owner owner" +
                        " inner join owner.user userOwner" +
                        " where ( userOwner.id= :userId or admins.id=:userId)" +
                        " and  org.deleteStatus = false" +
                        " group by org.id",
                OrganizationEntity.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

    @Override
    public OrganizationEntity findByDiscountId(UUID discountId) {
        return em.createQuery(
                "select org" +
                        " from DiscountEntity d  " +
                        " inner join d.organization org" +
                        " where d.id = :id",
                OrganizationEntity.class)
                .setParameter("id", discountId)
                .getSingleResult();
    }

    @Override
    public OrganizationEntity findByServiceId(UUID serviceId) {
        return em.createQuery(
                "select org" +
                        " from ServiceEntity service  " +
                        " inner join service.organization org" +
                        " where service.id = :id",
                OrganizationEntity.class)
                .setParameter("id", serviceId)
                .getSingleResult();
    }


    @Override
    public OrganizationEntity findByIdWithSettingPhonesTimesheetGallery(UUID organizationId) {
        return em.createQuery(
                "select o" +
                        " from OrganizationEntity o  " +
                        " left join o.phones" +
                        " left join o.timesheet" +
                        " left join o.gallery" +
                        " left join o.services" +
                        " where o.id = :id",
                OrganizationEntity.class)
                .setParameter("id", organizationId)
                .getSingleResult();
    }
}
