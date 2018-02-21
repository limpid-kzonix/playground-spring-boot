package com.omniesoft.commerce.persistence.projection.card.handbook;

import java.util.Set;
import java.util.UUID;

public interface HandbookSummary {
    UUID getId();

    String getName();

    String getAddress();

    String getImageId();

    Set<HandbookPhoneSummary> getPhones();

    Set<HandbookTagSummary> getTags();
}
