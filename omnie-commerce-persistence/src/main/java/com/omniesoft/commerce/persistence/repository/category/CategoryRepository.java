package com.omniesoft.commerce.persistence.repository.category;

import com.omniesoft.commerce.persistence.entity.category.CategoryEntity;
import com.omniesoft.commerce.persistence.projection.category.CategoryNamesPjn;
import com.omniesoft.commerce.persistence.projection.category.CategorySummary;
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
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, UUID> {

    CategoryEntity findByEngName(String engName);

    @Query("select c from CategoryEntity c")
    List<CategorySummary> findAllCategories();

    @Query("select c from CategoryEntity c")
    Page<CategoryNamesPjn> findAllOnlyWithNames(Pageable page);

    @Query("select COUNT(distinct o)" +
            " from OrganizationEntity o" +
            " inner join o.services s" +
            " inner join s.subCategories sc" +
            " where sc.category.id = :id")
    int countOrganizationByCategoryId(@Param("id") UUID categoryId);

    @Query("select COUNT(distinct s)" +
            " from ServiceEntity s" +
            " inner join s.subCategories sc" +
            " where sc.category.id = :id")
    int countServicesByCategoryId(@Param("id") UUID categoryId);

}
