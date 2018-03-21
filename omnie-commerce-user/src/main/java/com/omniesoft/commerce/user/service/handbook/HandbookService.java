package com.omniesoft.commerce.user.service.handbook;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookEntity;
import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookSummary;
import com.omniesoft.commerce.user.controller.handbook.payload.HandbookPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HandbookService {

    Page<HandbookPayload> getHandbookOrganization(Pageable pageable, String searchPattern);

    Page<HandbookSummary> getHandbook(Pageable pageable, String searchPattern);

    Page<HandbookSummary> getMyHandbookItems(Pageable pageable, String searchPattern, UserEntity userEntity);

    HandbookEntity proposeHandbook(HandbookPayload handbookPayload, UserEntity userEntity);

    HandbookEntity updateHandbook(UUID id, HandbookPayload handbookEntity, UserEntity userEntity);

    void deleteHandbookItem(UUID id, UserEntity userEntity);
}
