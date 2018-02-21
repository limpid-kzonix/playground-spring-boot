package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.ServiceLogPayload;
import com.omniesoft.commerce.statistic.models.entities.ServiceViewsLogEntity;
import com.omniesoft.commerce.statistic.models.repositories.ServiceViewsLogRepository;
import com.omniesoft.commerce.statistic.models.services.ServiceViewsLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceViewsLogServiceImpl implements ServiceViewsLogService {

    private ServiceViewsLogRepository serviceShowsLogRepository;

    @Override
    public void insert(ServiceLogPayload logPayload) {

        List<ServiceViewsLogEntity> collect = logPayload.getServices().stream().map(uuid ->
                new ServiceViewsLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());

        serviceShowsLogRepository.insert(collect);
    }
}
