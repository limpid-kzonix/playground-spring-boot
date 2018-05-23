package com.omniesoft.commerce.user.controller.temp;

import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookSummary;

import java.util.UUID;

public interface ITemporaryHandbookService {
    HandbookSummary getHandbook(UUID id);

    void deleteHandbook(UUID id);

    HandbookSummary addPhone(UUID handbookId, String phone);

    HandbookSummary deletePhone(UUID handbookId, UUID phoneId);

    HandbookSummary addTag(UUID handbookId, String tag);

    HandbookSummary deleteTag(UUID handbookId, UUID tagId);

    HandbookSummary save(SaveHandbookPl payload);
}
