package com.omniesoft.commerce.persistence.repository.category;

import com.omniesoft.commerce.persistence.entity.category.CategoryEntity;
import com.omniesoft.commerce.persistence.projection.category.CategorySummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 14.07.17
 */
@Transactional
public interface CategoryRepository extends CrudRepository<CategoryEntity, UUID> {

    CategoryEntity findByEngName(String engName);

    @Query("" +
            "select " +
            "   c " +
            "from CategoryEntity c ")
    List<CategorySummary> findAllCategories();

}
