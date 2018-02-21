package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.projection.organization.DiscountSummary;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 24.10.17
 */
@Transactional
public interface DiscountRepository extends PagingAndSortingRepository<DiscountEntity, UUID>, DiscountRepositoryCustom {
    List<DiscountSummary> findByOrganizationId(UUID organizationId);

    List<DiscountEntity> findByAssociatedServicesContains(UUID serviceEntity);
}
