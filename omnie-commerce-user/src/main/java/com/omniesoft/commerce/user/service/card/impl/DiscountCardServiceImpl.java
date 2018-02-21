package com.omniesoft.commerce.user.service.card.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.DiscountCardEntity;
import com.omniesoft.commerce.persistence.projection.card.DiscountCardSummary;
import com.omniesoft.commerce.persistence.repository.card.DiscountCardRepository;
import com.omniesoft.commerce.user.controller.card.payload.DiscountCardPayload;
import com.omniesoft.commerce.user.service.card.DiscountCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscountCardServiceImpl implements DiscountCardService {


    DiscountCardRepository discountCardRepository;

    @Autowired
    public DiscountCardServiceImpl(DiscountCardRepository discountCardRepository) {
        this.discountCardRepository = discountCardRepository;
    }

    @Override
    public Page<DiscountCardSummary> getDiscountCard(UserEntity userEntity, Pageable pageable) {
        return null;
    }

    @Override
    public DiscountCardEntity createDiscountCard(DiscountCardPayload discountCardPayload, UserEntity userEntity) {
        DiscountCardEntity discountCardEntity = prepareDiscountCard(discountCardPayload, new DiscountCardEntity());
        discountCardEntity.setUser(userEntity);
        return discountCardRepository.save(discountCardEntity);
    }


    @Override
    public DiscountCardEntity updateDiscountCard(DiscountCardPayload discountCardPayload, UserEntity userEntity) {
        DiscountCardEntity discountCardEntity = discountCardRepository.findByUserAndId(userEntity, discountCardPayload.getId());
        if (discountCardEntity == null) {
            throw new UsefulException();
        }
        prepareDiscountCard(discountCardPayload, discountCardEntity);
        discountCardEntity.setUser(userEntity);
        return discountCardRepository.save(discountCardEntity);
    }

    @Override
    public void deleteDiscountCard(UUID id, UserEntity userEntity) {
        if (discountCardRepository.findByUserAndId(userEntity, id) == null) {
            throw new UsefulException();
        }
        discountCardRepository.delete(id);
    }

    private DiscountCardEntity prepareDiscountCard(DiscountCardPayload discountCardPayload, DiscountCardEntity discountCardEntity) {
        discountCardEntity.setCode(discountCardPayload.getCode());
        discountCardEntity.setFormat(discountCardPayload.getFormat());
        discountCardEntity.setImage(discountCardPayload.getImage());
        discountCardEntity.setName(discountCardPayload.getName());
        discountCardEntity.setFormat(discountCardPayload.getFormat());
        return discountCardEntity;
    }
}
