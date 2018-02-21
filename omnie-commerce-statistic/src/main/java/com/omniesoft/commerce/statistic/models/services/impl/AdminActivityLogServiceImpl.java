package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.AdminLogPayload;
import com.omniesoft.commerce.statistic.models.entities.AdminActivityLogEntity;
import com.omniesoft.commerce.statistic.models.enums.AdminActionType;
import com.omniesoft.commerce.statistic.models.repositories.AdminActivityLogRepository;
import com.omniesoft.commerce.statistic.models.services.AdminActivityLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminActivityLogServiceImpl implements AdminActivityLogService {

    private AdminActivityLogRepository adminActivityLogRepository;

    @Override
    public void insert(AdminLogPayload logPayload, AdminActionType type) {
        List<AdminActivityLogEntity> collect = logPayload.getOrganizations().stream().map(uuid ->
                new AdminActivityLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        type,
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());


        adminActivityLogRepository.insert(collect);
    }
}
