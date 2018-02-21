package com.omniesoft.commerce.persistence.projection.organization;

import java.time.LocalTime;
import java.util.UUID;

public interface OrganizationTimeSheetSummary {

    public UUID getId();

    public Integer getDay();

    public LocalTime getWorkStart();

    public LocalTime getWorkEnd();

    public LocalTime getBreakStart();

    public LocalTime getBreakEnd();
}
