package com.omniesoft.commerce.user.service.handbook.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookPhoneEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookTagEntity;
import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookSummary;
import com.omniesoft.commerce.persistence.repository.card.HandbookRepository;
import com.omniesoft.commerce.user.controller.handbook.payload.HandbookPayload;
import com.omniesoft.commerce.user.service.handbook.HandbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class HandbookServiceImpl implements HandbookService {

    private HandbookRepository handbookRepository;

    @Autowired
    public HandbookServiceImpl(HandbookRepository handbookRepository) {

        this.handbookRepository = handbookRepository;
    }

    @Override
    public Page<HandbookSummary> getHandbook(Pageable pageable, String searchPattern) {

        return handbookRepository.findHandbookItemsWithPhonesAndTags(searchPattern.toLowerCase(), pageable);
    }

    @Override
    public Page<HandbookSummary> getMyHandbookItems(Pageable pageable, String searchPattern, UserEntity userEntity) {

        return handbookRepository.findAllByUserEntity(searchPattern.toLowerCase(), userEntity, pageable);
    }

    @Override
    public HandbookEntity proposeHandbook(HandbookPayload handbookPayload, UserEntity userEntity) {

        return prepareHandbookPropose(handbookPayload, userEntity, new HandbookEntity());
    }

    @Override
    public HandbookEntity updateHandbook(UUID handbookId, HandbookPayload handbookPayload, UserEntity userEntity) {

        HandbookEntity byUserEntityAndId = handbookRepository.findByUserEntityAndId(userEntity, handbookId);
        checkHandbook(byUserEntityAndId);
        return prepareHandbookPropose(handbookPayload, userEntity, byUserEntityAndId);
    }

    @Override
    public void deleteHandbookItem(UUID id, UserEntity userEntity) {

        HandbookEntity byUserEntityAndId = handbookRepository.findByUserEntityAndId(userEntity, id);
        checkHandbook(byUserEntityAndId);
        handbookRepository.delete(id);
    }

    private void checkHandbook(HandbookEntity byUserEntityAndId) {

        if (byUserEntityAndId == null) {
            throw new UsefulException(UserModuleErrorCodes.HANDBOOK_ITEM_NOT_EXIST);
        }
    }

    private HandbookEntity prepareHandbookPropose(HandbookPayload handbookPayload, UserEntity userEntity,
                                                  HandbookEntity handbookEntity) {

        HandbookEntity handbook = prepareBaseHandbookData(handbookPayload, handbookEntity);
        handbook.setAcceptStatus(false);
        setPhones(handbookPayload, handbook);
        setTags(handbookPayload, handbook);
        handbook.setUserEntity(userEntity);
        return handbookRepository.save(handbook);
    }

    private void setTags(HandbookPayload handbookPayload, HandbookEntity handbookEntity) {

        setHandbookTagEntities(handbookPayload, handbookEntity);
    }

    private void setPhones(HandbookPayload handbookPayload, HandbookEntity handbookEntity) {

        setHandbookPhoneEntities(handbookPayload, handbookEntity);
    }

    private void setHandbookTagEntities(HandbookPayload handbookPayload, HandbookEntity handbookEntity) {

        Set<HandbookTagEntity> handbookTagEntities = new HashSet<>();
        handbookPayload.getTags().forEach(tagPayload -> {
            HandbookTagEntity handbookTagEntity = new HandbookTagEntity();
            handbookTagEntity.setTag(tagPayload.getName());
            handbookTagEntity.setId(tagPayload.getId());
            handbookTagEntity.setHandbook(handbookEntity);
            handbookTagEntities.add(handbookTagEntity);
        });
        handbookEntity.setTags(handbookTagEntities);
    }

    private void setHandbookPhoneEntities(HandbookPayload handbookPayload, HandbookEntity handbookEntity) {

        Set<HandbookPhoneEntity> handbookPhoneEntities = new HashSet<>();
        handbookPayload.getPhones().forEach(phonePayload -> {
            HandbookPhoneEntity handbookPhoneEntity = new HandbookPhoneEntity();
            handbookPhoneEntity.setPhone(phonePayload.getPhone());
            handbookPhoneEntity.setId(phonePayload.getId());
            handbookPhoneEntity.setHandbook(handbookEntity);
            handbookPhoneEntities.add(handbookPhoneEntity);
        });
        handbookEntity.setPhones(handbookPhoneEntities);
    }

    private HandbookEntity prepareBaseHandbookData(HandbookPayload handbookPayload, HandbookEntity handbookEntity) {

        handbookEntity.setName(handbookPayload.getName());
        handbookEntity.setAddress(handbookPayload.getAddress());
        handbookEntity.setImageId(handbookPayload.getImage());
        return handbookEntity;
    }


}
