package com.omniesoft.commerce.user.service.card;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.DiscountCardEntity;
import com.omniesoft.commerce.persistence.projection.card.DiscountCardSummary;
import com.omniesoft.commerce.user.controller.card.payload.DiscountCardPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DiscountCardService {

    Page<DiscountCardSummary> getDiscountCard(UserEntity userEntity, Pageable pageable);

    DiscountCardEntity createDiscountCard(DiscountCardPayload discountCardPayload, UserEntity userEntity);

    DiscountCardEntity updateDiscountCard(DiscountCardPayload discountCardPayload, UserEntity userEntity);

    void deleteDiscountCard(UUID id, UserEntity userEntity);

}
