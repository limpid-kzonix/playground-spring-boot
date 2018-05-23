package com.omniesoft.commerce.user.controller.temp;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookPhoneEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookTagEntity;
import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookSummary;
import com.omniesoft.commerce.persistence.repository.card.HandbookPhoneRepository;
import com.omniesoft.commerce.persistence.repository.card.HandbookRepository;
import com.omniesoft.commerce.persistence.repository.card.HandbookTagRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TemporaryHandbookService implements ITemporaryHandbookService {
    private final HandbookRepository handbookRepository;
    private final HandbookTagRepository tagRepository;
    private final HandbookPhoneRepository phoneRepository;

    private static HandbookTagEntity mapTag(String tag, HandbookEntity handbook) {
        if (StringUtils.isBlank(tag)) {
            UsefulException usefulException = new UsefulException();
            usefulException.setDebugMessage("Empty/NotValid tag");
            usefulException.setExceptionCode(7890);
            throw usefulException;
        }
        HandbookTagEntity tagEntity = new HandbookTagEntity();
        tagEntity.setTag(tag);
        tagEntity.setHandbook(handbook);
        return tagEntity;
    }

    private static HandbookPhoneEntity mapPhone(String phone, HandbookEntity handbook) {
        if (StringUtils.isBlank(phone)) {
            UsefulException usefulException = new UsefulException();
            usefulException.setDebugMessage("Empty/NotValid Phone");
            usefulException.setExceptionCode(7890);
            throw usefulException;
        }
        HandbookPhoneEntity phoneEntity = new HandbookPhoneEntity();
        phoneEntity.setPhone(phone);
        phoneEntity.setHandbook(handbook);
        return phoneEntity;
    }

    @Override
    public HandbookSummary getHandbook(UUID id) {
        return handbookRepository.findById(id);
    }

    @Override
    public void deleteHandbook(UUID id) {
        handbookRepository.delete(id);
    }

    @Override
    public HandbookSummary addPhone(UUID handbookId, String phone) {
        HandbookEntity handbookEntity = handbookRepository.findOne(handbookId);
        HandbookPhoneEntity phoneEntity = new HandbookPhoneEntity();
        phoneEntity.setHandbook(handbookEntity);
        phoneEntity.setPhone(phone);
        phoneRepository.save(phoneEntity);
        return handbookRepository.findById(handbookId);
    }

    @Override
    public HandbookSummary deletePhone(UUID handbookId, UUID phoneId) {
        phoneRepository.delete(phoneId);
        return handbookRepository.findById(handbookId);

    }

    @Override
    public HandbookSummary addTag(UUID handbookId, String tag) {
        HandbookEntity handbookEntity = handbookRepository.findOne(handbookId);
        HandbookTagEntity tagEntity = new HandbookTagEntity();
        tagEntity.setHandbook(handbookEntity);
        tagEntity.setTag(tag);
        tagRepository.save(tagEntity);
        return handbookRepository.findById(handbookId);
    }

    @Override
    public HandbookSummary deleteTag(UUID handbookId, UUID tagId) {
        tagRepository.delete(tagId);
        return handbookRepository.findById(handbookId);

    }

    @Override
    public HandbookSummary save(SaveHandbookPl payload) {
        HandbookEntity handbookEntity = map(new HandbookEntity(), payload, true);
        HandbookEntity save = handbookRepository.save(handbookEntity);
        return handbookRepository.findById(save.getId());
    }

    private HandbookEntity map(@NotNull HandbookEntity handbook, SaveHandbookPl payload, boolean acceptStatus) {

        handbook.setName(payload.getName());
        handbook.setAddress(payload.getAddress());
        handbook.setImageId(payload.getImageId() == null ? handbook.getImageId() : payload.getImageId());
        handbook.setAcceptStatus(acceptStatus);
        handbook.setPhones(
                payload.getPhones().stream()
                        .map(phone -> mapPhone(phone, handbook))
                        .collect(Collectors.toSet()));

        handbook.setTags(
                payload.getTags().stream()
                        .map(tag -> mapTag(tag, handbook))
                        .collect(Collectors.toSet()));

        return handbook;
    }
}
