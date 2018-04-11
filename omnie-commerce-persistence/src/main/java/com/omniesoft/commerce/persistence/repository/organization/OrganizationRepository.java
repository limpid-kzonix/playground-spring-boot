package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.dto.organization.OrganizationRowDto;
import com.omniesoft.commerce.persistence.dto.organization.OrganizationRowExtendDto;
import com.omniesoft.commerce.persistence.entity.account.OwnerEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationCardSummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationHandbookSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.06.17
 */
@Transactional
public interface OrganizationRepository extends PagingAndSortingRepository<OrganizationEntity, UUID>, OrganizationRepositoryCustom {
    OrganizationEntity findByNameContaining(String name);

    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.organization.OrganizationRowDto( " +
            " o.id," +
            " o.name," +
            " o.email," +
            " o.logoId," +
            " o.deleteStatus," +
            " o.freezeStatus," +
            " o.createTime," +
            " ow.id," +
            " concat(owprofile.firstName, ' ', owprofile.lastName) as ownerName)" +
            " FROM OrganizationEntity as o " +
            " LEFT JOIN o.owner       as ow" +
            " LEFT JOIN ow.user       as owacc " +
            " LEFT JOIN owacc.profile as owprofile ")
    Page<OrganizationRowDto> getOrganizationPage(Pageable page);

    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.organization.OrganizationRowDto( " +
            " o.id," +
            " o.name," +
            " o.email," +
            " o.logoId," +
            " o.deleteStatus," +
            " o.freezeStatus," +
            " o.createTime," +
            " ow.id," +
            " concat(owprofile.firstName, ' ',owprofile.lastName) as ownerName)" +
            " FROM OrganizationEntity as o " +
            " LEFT JOIN o.owner       as ow" +
            " LEFT JOIN ow.user    as owacc" +
            " LEFT JOIN owacc.profile as owprofile" +
            " WHERE lower(o.name)             LIKE :filter%" +
            "      OR lower(o.email)          LIKE :filter%" +
            "      OR lower(owprofile.firstName) LIKE :filter%" +
            "      OR lower(owprofile.lastName)   LIKE :filter%")
    Page<OrganizationRowDto> getOrganizationPage(@Param("filter") String filter, Pageable page);

    @Query(value =
            "select " +
            "   new com.omniesoft.commerce.persistence.dto.organization.OrganizationRowExtendDto(" +
            "       org.id," +
            "       org.name," +
            "       org.email," +
            "       org.logoId," +
            "       org.backgroundImageId," +
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
            "   left   join            org.services service " +
            "   left   join            service.subCategories subcategory " +
            "   left   join            subcategory.category category " +
            "   left    join            org.mark mark" +
            "   left    join            org.inUsersFavorites inUsers " +
            "where " +
            "   (lower(org.name) like %:filter%)" +
            "   and (category.id = :c " +
            "   or subcategory.id = :c) and org.deleteStatus = false " +
            "group by" +
            "       org.id," +
            "       inUsers.id," +
            "       mark.id "
            ,
            countQuery =
                    "select " +
                    "   count(org) " +
                    "from OrganizationEntity org" +
                    "   inner   join            org.services service " +
                    "   inner   join            service.subCategories subcategory " +
                    "   left   join            subcategory.category category " +
                    "where " +
                    "   (lower(org.name) like %:filter%)" +
                    "   and (category.id = :c and :user <> null" +
                    "   or subcategory.id = :c) " +
                    "group by " +
                    "       org.id"
    )
    Page<OrganizationRowExtendDto> getOrganizationsByFilterAndCategoryAndUserEntity(@Param("filter") String filter, @Param("c") UUID category, @Param("user") UserEntity user, Pageable pageable);

    List<OrganizationCardSummary> findAllByOwner(OwnerEntity ownerAccount);

    @Query(value = "select org" +
            " from OrganizationEntity org" +
            " left join org.roles roles" +
            " left join roles.admins admins" +
            " inner join org.owner owner" +
            " inner join owner.user userOwner" +
            " where ( userOwner.id= :userId or admins.id=:userId)" +
            " and  org.deleteStatus = false" +
            " group by org"
    )
    Page<OrganizationCardSummary> findAllByOwnerUserIdOrRolesAdminsContains(@Param("userId") UUID user, Pageable pageable);


    @Query(value = "select org" +
            " from OrganizationEntity org" +
            " left join org.phones phones" +
            " left join org.services s " +
            " left join s.subCategories sc " +
            " left join sc.category c " +
            " where ((phones.phone like %:filter% ) " +
            " or (org.name like %:filter%) " +
            " or (s.name  like %:filter%) " +
            " or (sc.engName like %:filter%) " +
            " or (sc.rusName like %:filter%) " +
            " or (sc.ukrName like %:filter%) " +
            " or (c.engName like %:filter%) " +
            " or (c.rusName like %:filter%) " +
            " or (c.ukrName like %:filter%) " +
            " and  org.deleteStatus = false )" +
            " group by org.id "
    )
    Page<OrganizationHandbookSummary> findForHandbook(@Param("filter") String filter, Pageable pageable);


//    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.organization.OrganizationRowDto( " +
//            " o.id," +
//            " o.name," +
//            " o.email," +
//            " o.logoId," +
//            " s.deleteStatus," +
//            " s.freezeStatus," +
//            " o.createTime," +
//            " ow.id," +
//            " concat(owprofile.firstName, ' ', owprofile.lastName) as ownerName)" +
//            " FROM OrganizationEntity as o " +
//            " LEFT JOIN o.setting     as s" +
//            " LEFT JOIN o.owner       as ow" +
//            " LEFT JOIN ow.user       as owacc " +
//            " LEFT JOIN owacc.profile as owprofile " +
//            " WHERE owacc.id = :id")
//    Page<OrganizationRowDto> getOrganizationPageByUser(@Param("id") UUID id, Pageable page);

//    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.organization.OrganizationRowDto( " +
//            " o.id," +
//            " o.name," +
//            " o.email," +
//            " s.deleteStatus," +
//            " s.freezeStatus," +
//            " o.createTime," +
//            " ow.id," +
//            " concat(owprofile.firstName, ' ',owprofile.lastName) as ownerName)" +
//            " FROM OrganizationEntity as o " +
//            " LEFT JOIN o.setting     as s" +
//            " LEFT JOIN o.owner       as ow" +
//            " LEFT JOIN ow.user       as owacc" +
//            " LEFT JOIN owacc.profile as owprofile" +
//            " WHERE owacc.id = :id" +
//            "      AND ( lower(o.name)             LIKE :filter%" +
//            "           OR lower(o.email)          LIKE :filter%" +
//            "           OR lower(owprofile.firstName) LIKE :filter%" +
//            "           OR lower(owprofile.lastName)   LIKE :filter%)")
//    Page<OrganizationRowDto> getOrganizationPageByUser(@Param("id") UUID id,
//                                                       @Param("filter") String filter,
//                                                       Pageable page);
}