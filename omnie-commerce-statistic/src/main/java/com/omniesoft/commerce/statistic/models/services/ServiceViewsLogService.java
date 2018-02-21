package com.omniesoft.commerce.statistic.models.services;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.ServiceLogPayload;

public interface ServiceViewsLogService {
    void insert(ServiceLogPayload logPayload);

}
