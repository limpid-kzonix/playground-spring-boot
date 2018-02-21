package com.omniesoft.commerce.owner.service.service.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.owner.controller.service.payload.ServiceTimingPayload;
import com.omniesoft.commerce.owner.service.service.ServiceScopeTimingSettingService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceTimingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ServiceScopeTimingSettingServiceImpl implements ServiceScopeTimingSettingService {

    private final ServiceTimingRepository serviceTimingRepository;
    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public ServiceTimingPayload setServiceTimingSetting(ServiceTimingPayload payload, UUID org, UUID service,
                                                        UserEntity userEntity) {

        ServiceEntity serviceEntity = serviceRepository.findOne(service);

        LocalDateTime lastDateOfOrderForService = orderRepository.findLastDateOfOrderForService(service);
        ServiceTimingEntity serviceTimingEntity = serviceTimingRepository
                .findByServiceId(service, LocalDateTime.now());
        if (isUpdatable(serviceTimingEntity, lastDateOfOrderForService)) {
            serviceTimingEntity = prepareServiceTiming(payload, serviceTimingEntity, lastDateOfOrderForService);
        } else {
            serviceTimingEntity = prepareServiceTiming(payload, new ServiceTimingEntity(), lastDateOfOrderForService);
        }
        serviceTimingEntity.setService(serviceEntity);
        ServiceTimingEntity save = serviceTimingRepository.save(serviceTimingEntity);
        return map(save);
    }

    private boolean isUpdatable(ServiceTimingEntity serviceTimingEntity, LocalDateTime availableDate) {
        return serviceTimingEntity != null && serviceTimingEntity.getActiveFrom() != null && serviceTimingEntity
                .getActiveFrom()
                .equals(availableDate);
    }

    @Override
    public ServiceTimingPayload getServiceTimingSetting(UUID org, UUID service, UserEntity userEntity) {
        LocalDateTime currentDate = LocalDateTime.now();
        ServiceTimingEntity byServiceId = serviceTimingRepository.findByServiceId(service, currentDate);
        if (byServiceId == null) {
            throw new UsefulException("Service settings is not created", InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return map(byServiceId);
    }

    private ServiceTimingPayload map(ServiceTimingEntity byServiceId) {

        return modelMapper.map(byServiceId, ServiceTimingPayload.class);
    }

    private ServiceTimingEntity prepareServiceTiming(ServiceTimingPayload payload, ServiceTimingEntity
            serviceTimingEntity, LocalDateTime activeFrom) {
        serviceTimingEntity.setMaxDiscount(payload.getMaxDiscount());
        serviceTimingEntity.setSlotCount(payload.getSlotCount());
        serviceTimingEntity.setPauseMillis(payload.getPauseMillis());
        serviceTimingEntity.setMinDuration(payload.getMinDuration());
        serviceTimingEntity.setDurationMillis(payload.getDurationMillis());
        serviceTimingEntity.setActiveFrom(activeFrom);
        return serviceTimingEntity;
    }

}
