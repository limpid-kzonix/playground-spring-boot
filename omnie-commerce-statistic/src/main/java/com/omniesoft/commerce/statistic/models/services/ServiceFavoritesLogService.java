package com.omniesoft.commerce.statistic.models.services;

import com.omniesoft.commerce.common.ws.statistic.impl.enums.FavoriteType;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.ServiceLogPayload;

public interface ServiceFavoritesLogService {
    void insert(ServiceLogPayload logPayload, FavoriteType type);
}
