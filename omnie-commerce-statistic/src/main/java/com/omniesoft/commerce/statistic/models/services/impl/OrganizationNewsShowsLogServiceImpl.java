package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.NewsLogPayload;
import com.omniesoft.commerce.statistic.models.entities.OrganizationNewsShowsLogEntity;
import com.omniesoft.commerce.statistic.models.repositories.OrganizationNewsShowsLogRepository;
import com.omniesoft.commerce.statistic.models.services.OrganizationNewsShowsLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationNewsShowsLogServiceImpl implements OrganizationNewsShowsLogService {

    private OrganizationNewsShowsLogRepository newsShowsLogRepository;

    @Override
    public void insert(NewsLogPayload logPayload) {

        List<OrganizationNewsShowsLogEntity> collect = logPayload.getNews().stream().map(uuid ->
                new OrganizationNewsShowsLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());


        newsShowsLogRepository.insert(collect);
    }
}
