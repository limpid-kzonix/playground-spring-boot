package com.omniesoft.commerce.statistic.models.services;

import com.omniesoft.commerce.common.ws.statistic.impl.enums.FavoriteType;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.OrgLogPayload;

public interface OrganizationFavoritesLogService {
    void insert(OrgLogPayload logPayload, FavoriteType type);
}
