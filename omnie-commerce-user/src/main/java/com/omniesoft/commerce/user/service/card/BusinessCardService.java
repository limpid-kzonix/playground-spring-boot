package com.omniesoft.commerce.user.service.card;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.BusinessCardEntity;
import com.omniesoft.commerce.persistence.projection.card.BusinessCardSummary;
import com.omniesoft.commerce.user.controller.card.payload.BusinessCardPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BusinessCardService {

    Page<BusinessCardSummary> getBusinessCards(UserEntity userEntity, Pageable pageable);

    BusinessCardEntity createDiscountCard(BusinessCardPayload discountCardPayload, UserEntity userEntity);

    BusinessCardEntity updateDiscountCard(BusinessCardPayload businessCardPayload, UserEntity userEntity);

    void deleteDiscountCard(UUID id, UserEntity userEntity);
}
