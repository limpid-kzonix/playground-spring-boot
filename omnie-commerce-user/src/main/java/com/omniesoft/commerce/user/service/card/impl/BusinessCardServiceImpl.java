package com.omniesoft.commerce.user.service.card.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.BusinessCardEntity;
import com.omniesoft.commerce.persistence.projection.card.BusinessCardSummary;
import com.omniesoft.commerce.persistence.repository.card.BusinessCardRepository;
import com.omniesoft.commerce.user.controller.card.payload.BusinessCardPayload;
import com.omniesoft.commerce.user.service.card.BusinessCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusinessCardServiceImpl implements BusinessCardService {


    private BusinessCardRepository businessCardRepository;

    @Autowired
    public BusinessCardServiceImpl(BusinessCardRepository businessCardRepository) {
        this.businessCardRepository = businessCardRepository;
    }

    @Override
    public Page<BusinessCardSummary> getBusinessCards(UserEntity userEntity, Pageable pageable) {
        return businessCardRepository.findAllByUser(userEntity, pageable);
    }

    @Override
    public BusinessCardEntity createDiscountCard(BusinessCardPayload businessCardPayload, UserEntity userEntity) {
        BusinessCardEntity businessCardEntity = prepareBusinessCard(businessCardPayload, new BusinessCardEntity());
        businessCardEntity.setUser(userEntity);
        return businessCardRepository.save(businessCardEntity);
    }

    @Override
    public BusinessCardEntity updateDiscountCard(BusinessCardPayload businessCardPayload, UserEntity userEntity) {
        BusinessCardEntity businessCardEntity = businessCardRepository.findByUserAndId(userEntity, businessCardPayload.getId());
        if (businessCardEntity == null) {
            throw new UsefulException();
        }
        businessCardEntity = prepareBusinessCard(businessCardPayload, businessCardEntity);
        businessCardEntity.setUser(userEntity);
        return businessCardRepository.save(businessCardEntity);
    }

    @Override
    public void deleteDiscountCard(UUID id, UserEntity userEntity) {
        if (businessCardRepository.findByUserAndId(userEntity, id) == null) {
            throw new UsefulException();
        }
        businessCardRepository.delete(id);
    }


    private BusinessCardEntity prepareBusinessCard(BusinessCardPayload businessCardPayload, BusinessCardEntity businessCardEntity) {
        businessCardEntity.setComment(businessCardPayload.getComment());
        businessCardEntity.setImage(businessCardPayload.getImage());
        businessCardEntity.setEmail(businessCardPayload.getEmail());
        businessCardEntity.setName(businessCardPayload.getName());
        businessCardEntity.setPhone(businessCardPayload.getPhone());
        return businessCardEntity;
    }
}
