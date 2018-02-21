package com.omniesoft.commerce.statistic.models.services.impl;

import com.omniesoft.commerce.common.ws.statistic.impl.enums.FavoriteType;
import com.omniesoft.commerce.common.ws.statistic.impl.payload.ServiceLogPayload;
import com.omniesoft.commerce.statistic.models.entities.ServiceFavoritesLogEntity;
import com.omniesoft.commerce.statistic.models.repositories.ServiceFavoritesLogRepository;
import com.omniesoft.commerce.statistic.models.services.ServiceFavoritesLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceFavoritesLogServiceImpl implements ServiceFavoritesLogService {

    private ServiceFavoritesLogRepository favoritesLogRepository;

    @Override
    public void insert(ServiceLogPayload logPayload, FavoriteType type) {

        List<ServiceFavoritesLogEntity> collect = logPayload.getServices().stream().map(uuid ->
                new ServiceFavoritesLogEntity(
                        null,
                        logPayload.getUserId().toString(),
                        uuid.toString(),
                        type,
                        logPayload.getDateTime()
                )
        ).collect(Collectors.toList());

        favoritesLogRepository.insert(collect);
    }
}
