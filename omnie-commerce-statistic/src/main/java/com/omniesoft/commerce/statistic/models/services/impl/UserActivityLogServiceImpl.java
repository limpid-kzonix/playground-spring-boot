package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.UserLogPayload;
import com.omniesoft.commerce.statistic.models.entities.UserActivityLogEntity;
import com.omniesoft.commerce.statistic.models.entities.UserSearchingLogEntity;
import com.omniesoft.commerce.statistic.models.enums.UserActionType;
import com.omniesoft.commerce.statistic.models.repositories.UserActivityLogRepository;
import com.omniesoft.commerce.statistic.models.repositories.UserSearchingLogRepository;
import com.omniesoft.commerce.statistic.models.services.UserActivityLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserActivityLogServiceImpl implements UserActivityLogService {

    private UserActivityLogRepository userActivityLogRepository;
    private UserSearchingLogRepository userSearchingLogRepository;

    @Override
    public void insert(UserLogPayload logPayload, UserActionType type) {

        UserActivityLogEntity entity = new UserActivityLogEntity();
        entity.setDateTime(logPayload.getDateTime());
        entity.setType(type);
        entity.setUserId(logPayload.getUserId().toString());

        userActivityLogRepository.insert(entity);
    }

    @Override
    public void insertToSearchingLog(UserLogPayload logPayload, String searchPattern) {
        UserSearchingLogEntity entity = new UserSearchingLogEntity();
        entity.setDateTime(logPayload.getDateTime());
        entity.setPattern(searchPattern);
        entity.setUserId(logPayload.getUserId().toString());

        userSearchingLogRepository.insert(entity);
    }
}
