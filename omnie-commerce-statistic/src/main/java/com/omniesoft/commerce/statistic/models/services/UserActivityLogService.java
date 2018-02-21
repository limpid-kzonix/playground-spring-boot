package com.omniesoft.commerce.statistic.models.services;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.UserLogPayload;
import com.omniesoft.commerce.statistic.models.enums.UserActionType;

public interface UserActivityLogService {
    void insert(UserLogPayload logPayload, UserActionType type);

    void insertToSearchingLog(UserLogPayload logPayload, String searchPattern);
}
