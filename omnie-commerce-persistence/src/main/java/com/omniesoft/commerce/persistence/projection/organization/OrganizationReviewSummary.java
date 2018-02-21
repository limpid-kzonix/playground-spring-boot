package com.omniesoft.commerce.persistence.projection.organization;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrganizationReviewSummary {

    public UUID getId();

    public Integer getMark();

    public String getText();

    public Boolean getAcceptStatus();

    public LocalDateTime getCreateTime();

    public LocalDateTime getUpdateTime();
}
