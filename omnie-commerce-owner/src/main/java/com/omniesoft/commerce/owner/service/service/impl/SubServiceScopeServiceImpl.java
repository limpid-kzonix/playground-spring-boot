package com.omniesoft.commerce.owner.service.service.impl;

import com.omniesoft.commerce.common.converter.SubServicePriceConverter;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.InternalErrorCodes;
import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.owner.service.service.SubServiceScopeService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServicePriceEntity;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import com.omniesoft.commerce.persistence.repository.service.SubServicePriceRepository;
import com.omniesoft.commerce.persistence.repository.service.SubServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SubServiceScopeServiceImpl implements SubServiceScopeService {

    private final SubServiceRepository subServiceRepository;

    private final SubServicePriceRepository subServicePriceRepository;

    private final ServiceRepository serviceRepository;

    private final SubServicePriceConverter subServicePriceConverter;

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public SubServicePayload save(SubServicePayload subServicePayload, UUID org, UUID service,
                                  UserEntity userEntity) {


        ServiceEntity one = getServiceById(service);

        LocalDateTime now = orderRepository.findLastDateOfOrderForService(service);

        SubServiceEntity subServiceEntity = new SubServiceEntity();
        subServiceEntity.setCreateByUser(userEntity);
        subServiceEntity.setService(one);
        subServiceEntity.setName(subServicePayload.getName());
        subServiceEntity.setDeleteStatus(false);
        SubServiceEntity savedSubService = subServiceRepository.save(subServiceEntity);

        return setSubServicePrice(subServicePayload, now, savedSubService);
    }

    private SubServicePriceEntity prepareSubServicePrice(SubServicePayload subServicePayload, LocalDateTime now,
                                                         SubServiceEntity savedSubService) {

        SubServicePriceEntity subServicePriceEntity = subServicePriceRepository.findBySubServiceIdAndActiveFrom(savedSubService.getId(), now);
        if (subServicePriceEntity == null)
            subServicePriceEntity = new SubServicePriceEntity();

        subServicePriceEntity.setActiveFrom(now);
        subServicePriceEntity.setDurationMillis(subServicePayload.getDurationMillis());
        subServicePriceEntity.setExpense(subServicePayload.getExpense());
        subServicePriceEntity.setMaxCount(subServicePayload.getMaxCount());
        subServicePriceEntity.setMeasurementUnit(subServicePayload.getMeasurementUnit());
        subServicePriceEntity.setMinCount(subServicePayload.getMinCount());
        subServicePriceEntity.setPrice(subServicePayload.getPrice());
        subServicePriceEntity.setMaxDiscount(subServicePayload.getMaxDiscount());
        subServicePriceEntity.setSubService(savedSubService);
        return subServicePriceEntity;
    }

    @Override
    @Transactional
    public SubServicePayload update(SubServicePayload subServicePayload, UUID org, UUID service, UUID subService,
                                    UserEntity userEntity) {


        LocalDateTime now = orderRepository.findLastDateOfOrderForService(service);

        SubServiceEntity subServiceEntity = subServiceRepository.findByIdAndOrganizationId(subService, org);
        if (subServiceEntity == null) {
            throw new UsefulException("Sub-service with id [" + subService + "]",
                    InternalErrorCodes.RESOURCE_NOT_FOUND);
        }

        subServiceEntity.setName(subServicePayload.getName());
        SubServiceEntity savedSubService = subServiceRepository.save(subServiceEntity);

        return setSubServicePrice(subServicePayload, now, savedSubService);
    }

    private SubServicePayload setSubServicePrice(SubServicePayload subServicePayload, LocalDateTime now,
                                                 SubServiceEntity savedSubService) {

        SubServicePriceEntity subServicePriceEntity = prepareSubServicePrice(
                subServicePayload, now, savedSubService);

        subServicePriceRepository.save(subServicePriceEntity);
        subServicePayload.setId(savedSubService.getId());
        return subServicePayload;
    }

    @Override
    @Transactional
    public List<SubServicePayload> findSubServiceByService(UUID org, UUID service, UserEntity userEntity) {

        List<SubServiceEntity> subServiceEntities = subServiceRepository
                .findByIdAndOrganizationIdWithPrice(service, org);
        return subServicePriceConverter
                .convert(subServiceEntities, LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
    }

    @Override
    public void deleteSubService(UUID subServiceId, UUID service, UUID org, UserEntity userEntity) {

        subServiceRepository.changeDeleteStatus(subServiceId, true);
    }

    private ServiceEntity getServiceById(UUID service) {

        ServiceEntity one = serviceRepository.findOne(service);
        if (one == null) {
            throw new UsefulException(service.toString(), InternalErrorCodes.RESOURCE_NOT_FOUND);
        }
        return one;
    }
}
