package com.omniesoft.commerce.statistic.models.services;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.OrgLogPayload;

public interface OrganizationShowsLogService {
    void insert(OrgLogPayload logPayload);
}
