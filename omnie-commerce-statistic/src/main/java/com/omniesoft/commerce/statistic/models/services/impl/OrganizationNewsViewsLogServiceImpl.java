package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.NewsLogPayload;
import com.omniesoft.commerce.statistic.models.entities.OrganizationNewsViewsLogEntity;
import com.omniesoft.commerce.statistic.models.repositories.OrganizationNewsViewsLogRepository;
import com.omniesoft.commerce.statistic.models.services.OrganizationNewsViewsLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationNewsViewsLogServiceImpl implements OrganizationNewsViewsLogService {

    private OrganizationNewsViewsLogRepository newsViewsLogRepository;

    @Override
    public void insert(NewsLogPayload logPayload) {
        List<OrganizationNewsViewsLogEntity> collect = logPayload.getNews().stream().map(uuid ->
                new OrganizationNewsViewsLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());


        newsViewsLogRepository.insert(collect);
    }
}
