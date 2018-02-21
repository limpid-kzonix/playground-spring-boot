package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.payload.OrgLogPayload;
import com.omniesoft.commerce.statistic.models.entities.OrganizationShowsLogEntity;
import com.omniesoft.commerce.statistic.models.repositories.OrganizationShowsLogRepository;
import com.omniesoft.commerce.statistic.models.services.OrganizationShowsLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationShowsLogServiceImpl implements OrganizationShowsLogService {

    private OrganizationShowsLogRepository organizationShowsLogRepository;

    @Override
    public void insert(OrgLogPayload logPayload) {

        List<OrganizationShowsLogEntity> collect = logPayload.getOrganizations().stream().map(uuid ->
                new OrganizationShowsLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());


        organizationShowsLogRepository.insert(collect);
    }
}
