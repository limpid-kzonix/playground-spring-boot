package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.OrgLogPayload;
import com.omniesoft.commerce.statistic.models.entities.OrganizationViewsLogEntity;
import com.omniesoft.commerce.statistic.models.repositories.OrganizationViewsLogRepository;
import com.omniesoft.commerce.statistic.models.services.OrganizationViewsLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationViewsLogServiceImpl implements OrganizationViewsLogService {
    private OrganizationViewsLogRepository organizationShowsLogRepository;

    @Override
    public void insert(OrgLogPayload logPayload) {
        List<OrganizationViewsLogEntity> collect = logPayload.getOrganizations().stream().map(uuid ->
                new OrganizationViewsLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());


        organizationShowsLogRepository.insert(collect);
    }
}
