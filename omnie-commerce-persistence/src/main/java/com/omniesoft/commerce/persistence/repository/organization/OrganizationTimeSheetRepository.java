package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.organization.OrganizationTimeSheetEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationTimeSheetSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 13.07.17
 */

@Transactional
public interface OrganizationTimeSheetRepository extends CrudRepository<OrganizationTimeSheetEntity, UUID>,
        OrganizationTimeSheetRepositoryCustom {
    Page<OrganizationTimeSheetSummary> findAllByOrganizationId(UUID id, Pageable pageable);

    List<OrganizationTimeSheetEntity> findAllByOrganizationId(UUID id);

    List<OrganizationTimeSheetEntity> findAllByOrganizationIdAndDay(UUID id, DayOfWeek day);

    void deleteAllByOrganizationIdAndDay(UUID id, DayOfWeek day);
}
