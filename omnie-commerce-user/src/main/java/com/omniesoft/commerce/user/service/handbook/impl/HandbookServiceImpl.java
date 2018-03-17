package com.omniesoft.commerce.user.service.handbook.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookPhoneEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookTagEntity;
import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookSummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationHandbookSummary;
import com.omniesoft.commerce.persistence.repository.card.HandbookRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.user.controller.handbook.payload.HandbookPayload;
import com.omniesoft.commerce.user.controller.handbook.payload.HandbookPhonePayload;
import com.omniesoft.commerce.user.controller.handbook.payload.HandbookTagPayload;
import com.omniesoft.commerce.user.service.handbook.HandbookService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class HandbookServiceImpl implements HandbookService {

    private HandbookRepository handbookRepository;
    private OrganizationRepository organizationRepository;

    @Override
    public Page<HandbookPayload> getHandbookOrganization(Pageable pageable, String searchPattern) {
        return organizationRepository.findForHandbook(searchPattern, pageable).map(convertOrgToHandbook());
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
        } else if (byUserEntityAndId.getAcceptStatus() != null && byUserEntityAndId.getAcceptStatus()) {
            throw new UsefulException("Changing of this data is not allowed").withCode(UserModuleErrorCodes.NOT_ALLOWED_CHANGE_OPERATION);
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

    private Converter<OrganizationHandbookSummary, HandbookPayload> convertOrgToHandbook() {
        return (source) -> {
            HandbookPayload handbookPayload = new HandbookPayload();
            handbookPayload.setId(source.getId());
            handbookPayload.setAddress(source.getPlaceId());
            handbookPayload.setName(source.getName());
            handbookPayload.setImage(source.getLogoId());
            handbookPayload.setTags(source.getServices().stream().flatMap(s -> s.getSubCategories().stream().map(sub -> new HandbookTagPayload(sub.getId(), sub.getUkrName()))).collect(Collectors.toList()));
            handbookPayload.setPhones(source.getPhones().stream().map(hp -> new HandbookPhonePayload(hp.getId(), hp.getPhone())).collect(Collectors.toList()));
            return handbookPayload;
        };
    }


}
