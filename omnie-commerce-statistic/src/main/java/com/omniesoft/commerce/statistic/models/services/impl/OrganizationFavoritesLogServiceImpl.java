package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.enums.FavoriteType;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.OrgLogPayload;
import com.omniesoft.commerce.statistic.models.entities.OrganizationFavoritesLogEntity;
import com.omniesoft.commerce.statistic.models.repositories.OrganizationFavoritesLogRepository;
import com.omniesoft.commerce.statistic.models.services.OrganizationFavoritesLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationFavoritesLogServiceImpl implements OrganizationFavoritesLogService {

    private OrganizationFavoritesLogRepository organizationFavoritesLogRepository;

    @Override
    public void insert(OrgLogPayload logPayload, FavoriteType type) {

        List<OrganizationFavoritesLogEntity> collect = logPayload.getOrganizations().stream().map(uuid ->
                new OrganizationFavoritesLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        type,
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());


        organizationFavoritesLogRepository.insert(collect);
    }
}
