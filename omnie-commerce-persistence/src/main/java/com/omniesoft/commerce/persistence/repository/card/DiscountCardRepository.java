package com.omniesoft.commerce.persistence.repository.card;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.DiscountCardEntity;
import com.omniesoft.commerce.persistence.projection.card.DiscountCardSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface DiscountCardRepository extends PagingAndSortingRepository<DiscountCardEntity, UUID> {
    DiscountCardEntity findByUserAndId(UserEntity userEntity, UUID id);

    Page<DiscountCardSummary> findAllByUser(UserEntity userEntity, Pageable pageable);
}
