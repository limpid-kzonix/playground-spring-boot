package com.omniesoft.commerce.statistic.models.services;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.NewsLogPayload;

public interface OrganizationNewsShowsLogService {
    void insert(NewsLogPayload logPayload);
}
