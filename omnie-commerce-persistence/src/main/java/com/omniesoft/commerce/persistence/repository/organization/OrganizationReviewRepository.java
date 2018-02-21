package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationReviewSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface OrganizationReviewRepository
        extends PagingAndSortingRepository<OrganizationReviewEntity, UUID>, OrganizationReviewRepositoryCustom {

    Page<OrganizationReviewSummary> findAllByOrganizationIdAndAcceptStatusOrderByMarkDesc(UUID id, Boolean status, Pageable pageable);

    OrganizationReviewEntity findByUserAndOrganizationId(UserEntity userEntity, UUID organizationId);

    OrganizationReviewEntity findByIdAndOrganizationIdAndUserAndAcceptStatus(UUID reviewId, UUID organizationId, UserEntity
            userEntity, Boolean status);

    Page<OrganizationReviewSummary> findAllByOrganizationId(UUID organizationId, Pageable pageable);

    OrganizationReviewEntity findByIdAndOrganizationId(UUID reviewId, UUID organizationId);
}
