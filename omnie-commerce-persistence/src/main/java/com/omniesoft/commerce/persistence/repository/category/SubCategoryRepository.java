package com.omniesoft.commerce.persistence.repository.category;

import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.persistence.projection.category.SubCategorySummary;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 14.07.17
 */
@Transactional
public interface SubCategoryRepository extends CrudRepository<SubCategoryEntity, UUID> {

    List<SubCategoryEntity> findAll();

    List<SubCategorySummary> findAllByCategoryId(UUID id);

    List<SubCategorySummary> findAllByServiceIdAndServiceOrganizationId(UUID service, UUID org);

}
