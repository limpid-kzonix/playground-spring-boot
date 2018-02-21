package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.ServiceLogPayload;
import com.omniesoft.commerce.statistic.models.entities.ServiceShowsLogEntity;
import com.omniesoft.commerce.statistic.models.repositories.ServiceShowsLogRepository;
import com.omniesoft.commerce.statistic.models.services.ServiceShowsLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceShowsLogServiceImpl implements ServiceShowsLogService {

    private ServiceShowsLogRepository serviceShowsLogRepository;

    @Override
    public void insert(ServiceLogPayload logPayload) {

        List<ServiceShowsLogEntity> collect = logPayload.getServices().stream().map(uuid ->
                new ServiceShowsLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());

        serviceShowsLogRepository.insert(collect);
    }
}
