package com.omniesoft.commerce.persistence.repository.category;

import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.persistence.projection.category.SubCategoryNamesPjn;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
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
 * @since 14.07.17
 */
@Transactional
public interface SubCategoryRepository extends PagingAndSortingRepository<SubCategoryEntity, UUID> {

    @Query("select sc from SubCategoryEntity sc join fetch sc.category where sc.id = :id")
    SubCategoryEntity findByIdJoinCategory(@Param("id") UUID id);

    List<SubCategoryEntity> findAll();

    List<SubCategorySummary> findAllByCategoryId(UUID id);

    List<SubCategorySummary> findAllByServiceIdAndServiceOrganizationId(UUID service, UUID org);

    @Query("select sc from SubCategoryEntity sc")
    Page<SubCategoryNamesPjn> findAllOnlyWithNames(Pageable pageable);

    Page<SubCategoryNamesPjn> findAllByCategoryId(UUID categoryId, Pageable pageable);

    @Query("select COUNT(distinct o)" +
            " from OrganizationEntity o" +
            " join o.services as s" +
            " join s.subCategories as sc" +
            " where sc.id = :id")
    int countOrganizationBySubCategoryId(@Param("id") UUID subCategoryId);

    @Query("select COUNT(distinct s)" +
            " from ServiceEntity s" +
            " join s.subCategories as sc" +
            " where sc.id = :id")
    int countServicesBySubCategoryId(@Param("id") UUID subCategoryId);


}
