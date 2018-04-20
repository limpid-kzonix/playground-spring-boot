package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.dto.service.ServiceRowAdminExtendDto;
import com.omniesoft.commerce.persistence.dto.service.ServiceRowDto;
import com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface ServiceRepository extends PagingAndSortingRepository<ServiceEntity, UUID>, ServiceRepositoryCustom {

    @Query("select s from ServiceEntity s " +
            "where " +
            " s.id = :sid " +
            "and s.deleteStatus = false ")
    ServiceEntity findOne(@Param("sid") UUID id);

    @Query("select s from ServiceEntity s " +
            "where s.organization.id = :id " +
            "and s.deleteStatus = false")
    List<ServiceEntity> findByOrganization_Id(@Param("id") UUID id);

    @Query("select s from ServiceEntity s " +
            "where s.organization.id = :oid " +
            "and s.id = :sid " +
            "and s.deleteStatus = false ")
    ServiceEntity findByIdAndOrganizationId(@Param("sid") UUID id, @Param("oid") UUID organizationId);

    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.service.ServiceRowDto( " +
            " s.id," +
            " s.name," +
            " s.logoId," +
            " s.deleteStatus," +
            " s.freezeStatus," +
            " s.createTime," +
            " o.id," +
            " o.name)" +
            " FROM ServiceEntity       s " +
            " LEFT JOIN s.organization o ")
    Page<ServiceRowDto> getPageService(Pageable page);


    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.service.ServiceRowDto( " +
            " s.id," +
            " s.name," +
            " s.logoId," +
            " s.deleteStatus," +
            " s.freezeStatus," +
            " s.createTime," +
            " o.id," +
            " o.name)" +
            " FROM ServiceEntity       s " +
            " LEFT JOIN s.organization o" +
            " WHERE lower(s.name) LIKE :filter%" +
            "       OR lower(o.name) LIKE :filter%")
    Page<ServiceRowDto> getServicePage(@Param("filter") String filter, Pageable page);

    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.service.ServiceRowDto( " +
            " s.id," +
            " s.name," +
            " s.logoId," +
            " s.deleteStatus," +
            " s.freezeStatus," +
            " s.createTime," +
            " o.id," +
            " o.name)" +
            " FROM ServiceEntity       s " +
            " LEFT JOIN s.organization o " +
            " WHERE o.id =:id")
    Page<ServiceRowDto> getServicePageByOrganization(@Param("id") UUID id,
                                                     Pageable page);


    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.service.ServiceRowDto( " +
            " s.id," +
            " s.name," +
            " s.logoId," +
            " s.deleteStatus," +
            " s.freezeStatus," +
            " s.createTime," +
            " o.id," +
            " o.name)" +
            " FROM ServiceEntity       s " +
            " LEFT JOIN s.organization o" +
            " WHERE  o.id = :id" +
            " AND (lower(s.name) LIKE :filter%" +
            "       OR lower(o.name) LIKE :filter%)")
    Page<ServiceRowDto> getServicePageByOrganization(@Param("id") UUID id,
                                                     @Param("filter") String filter,
                                                     Pageable page);

    @Query(value = "select" +
            "  new com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto(" +
            "    service.id," +
            "    service.name," +
            "    service.description," +
            "    service.logoId," +
            "    service.freezeStatus," +
            "    service.reason," +
            "    service.createTime," +
            "    (case when (select f.id.user.id" +
            "                from ServiceFavoriteEntity f " +
            "                where f.id.user =:user and f.id.service = service)" +
            "    is not null then true else false end)," +
            "    coalesce( mark.rating, 0)," +
            "    organization.id," +
            "    organization.name," +
            "    organization.placeId," +
            "    organization.longitude," +
            "    organization.latitude" +
            "  )" +
            " from ServiceEntity service" +
            "  left  join  service.subCategories subcategory" +
            "  left  join  subcategory.category category" +
            "  left  join  service.mark mark" +
            "  left  join  service.organization organization" +
            " where " +
            "  lower(service.name) like %:filter%" +
            "  and (category.id = :c or subcategory.id = :c)" +
            "  and service.deleteStatus = false" +
            " group by" +
            "  service.id," +
            "  organization.id," +
            "  mark.id",
            countQuery = "select" +
                    "  count(service) " +
                    " from ServiceEntity service" +
                    "  inner  join  service.subCategories subcategory " +
                    "  inner  join  subcategory.category category" +
                    " where " +
                    "  lower(service.name) like %:filter%" +
                    "  and (category.id = :c or subcategory.id = :c)" +
                    "  and service.deleteStatus = false" +
                    "  and :user <> null" +
                    " group by " +
                    "  service.id," +
                    "  category.id")
    Page<ServiceRowUserExtendDto> findServicesByFilterAndCategoryAndUserEntity(@Param("filter") String filter,
                                                                               @Param("c") UUID category,
                                                                               @Param("user") UserEntity user,
                                                                               Pageable pageable);


    @Query(value = "select" +
            " new com.omniesoft.commerce.persistence.dto.service.ServiceRowUserExtendDto(" +
            "  service.id," +
            "  service.name," +
            "  service.description," +
            "  service.logoId," +
            "  service.freezeStatus," +
            "  service.reason," +
            "  service.createTime," +
            "    (case when (select f.id.user.id" +
            "                from ServiceFavoriteEntity f " +
            "                where f.id.user =:user and f.id.service = service)" +
            "    is not null then true else false end)," +
            "  coalesce( mark.rating, 0)," +
            "  organization.id," +
            "  organization.name" +
            " )" +
            " from ServiceEntity service" +
            "    left  join  service.mark mark" +
            "    left  join  service.organization organization" +
            " where " +
            "    lower(service.name) like %:filter%" +
            "    and organization.id = :o" +
            "    and service.deleteStatus = false" +
            " group by" +
            "  service.id," +
            "  organization.id," +
            "  mark.id",
            countQuery = "select" +
                    " count(service)" +
                    " from ServiceEntity service" +
                    "  left  join  service.organization organization" +
                    " where" +
                    "  lower(service.name) like %:filter%" +
                    "  and organization.id = :o" +
                    "  and service.deleteStatus = false" +
                    "  and :user <> null" +
                    " group by" +
                    "  service.id," +
                    "  organization.id")
    Page<ServiceRowUserExtendDto> findOrganizationServices(@Param("filter") String filter,
                                                           @Param("o") UUID org,
                                                           @Param("user") UserEntity user,
                                                           Pageable pageable);


    @Query(value = "" +
            "select " +
            "   new com.omniesoft.commerce.persistence.dto.service.ServiceRowAdminExtendDto(" +
            "       service.id," +
            "       service.name," +
            "       service.description," +
            "       service.logoId," +
            "       service.freezeStatus," +
            "       service.reason," +
            "       service.createTime" +
            "   ) " +
            "from ServiceEntity service" +
            "   left   join            service.mark mark " +
            "   left   join             service.organization organization " +
            "where " +
            "   (organization.id = :o)" +
            "   and service.deleteStatus = false " +
            "group by" +
            "       service.id," +
            "       organization.id," +
            "       mark.id"
            ,
            countQuery = "" +
                    "select " +
                    "   count(service) " +
                    "from ServiceEntity service" +
                    "   inner   join             service.organization organization " +
                    "where " +
                    "    (organization.id = :o )" +
                    "   and service.deleteStatus = false " +
                    "group by " +
                    "       service.id, " +
                    "       organization.id "
    )
    List<ServiceRowAdminExtendDto> getServicesByOrganizationId(@Param("o") UUID org);


    @Query("" +
            "select " +
            "   s " +
            "from ServiceEntity s " +
            "   left join fetch s.subCategories subcategories " +
            "   left join fetch subcategories.category category " +
            "where " +
            "   s.id = :id " +
            "and" +
            "   s.deleteStatus = false  " +
            "")
    ServiceEntity findServiceWithCategories(@Param("id") UUID serviceId);

    @Query("" +
            "select " +
            "   s " +
            "from ServiceEntity s " +
            "   left join fetch s.subCategories subcategories " +
            "   left join fetch subcategories.category category " +
            "where " +
            "   s.organization.id = :id " +
            "and" +
            "   s.deleteStatus = false " +
            "")
    List<ServiceEntity> findServicesWithCategoriesByOrg(@Param("id") UUID org);

    @Query("" +
            "select " +
            "   s " +
            "from ServiceEntity s " +
            "   left join fetch s.subCategories subcategories " +
            "   left join fetch subcategories.category category " +
            "where " +
            "   s.id = :id " +
            "and" +
            "   s.deleteStatus = false " +
            "")
    ServiceEntity findOneServiceWithCategories(@Param("id") UUID service);


    Page<ServiceEntity> findAllByInUsersFavoritesContainsAndDeleteStatusFalse(UserEntity userEntity,
                                                                              Pageable pageable);

    @Query("select s from ServiceEntity s " +
            "where s.organization.id = :id " +
            "and s.deleteStatus = false")
    List<ServiceEntity> findAllByOrganizationId(@Param("id") UUID org);


}