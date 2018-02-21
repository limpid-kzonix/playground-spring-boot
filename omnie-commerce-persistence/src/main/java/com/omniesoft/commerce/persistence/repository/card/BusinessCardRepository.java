package com.omniesoft.commerce.persistence.repository.card;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.BusinessCardEntity;
import com.omniesoft.commerce.persistence.projection.card.BusinessCardSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BusinessCardRepository extends PagingAndSortingRepository<BusinessCardEntity, UUID> {
    BusinessCardEntity findByUserAndId(UserEntity userEntity, UUID id);

    Page<BusinessCardSummary> findAllByUser(UserEntity userEntity, Pageable pageable);
}
