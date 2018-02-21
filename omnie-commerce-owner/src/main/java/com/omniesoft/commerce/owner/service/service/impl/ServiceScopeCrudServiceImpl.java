package com.omniesoft.commerce.owner.service.service.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.owner.service.service.ServiceScopeCrudService;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ServiceScopeCrudServiceImpl implements ServiceScopeCrudService {

    private ServiceRepository serviceRepository;


    @Override
    public ServiceEntity find(UUID service) {

        return getServiceById(service);
    }

    private ServiceEntity getServiceById(UUID service) {

        ServiceEntity one = serviceRepository.findOne(service);
        if (one == null) {
            throw new UsefulException(service.toString(), InternalErrorCodes.RESOURCE_NOT_FOUND);
        } else if (one.getDeleteStatus()) {
            throw new UsefulException(InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return one;
    }
}
