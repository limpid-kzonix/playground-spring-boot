package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.organization.OrganizationPhoneEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationPhoneSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.07.17
 */
@Transactional
public interface OrganizationPhoneRepository extends CrudRepository<OrganizationPhoneEntity, UUID>,
        OrganizationPhoneRepositoryCustom {

    Page<OrganizationPhoneSummary> findAllByOrganizationId(UUID id, Pageable pageable);

    List<OrganizationPhoneSummary> findAllByOrganizationId(UUID id);

}
