package com.omniesoft.commerce.owner.service.service.impl;

import com.google.common.collect.Lists;
import com.omniesoft.commerce.common.converter.ServicePriceConverter;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.owner.service.service.ServicePriceTimeSettingValidator;
import com.omniesoft.commerce.owner.service.service.ServiceScopeCrudService;
import com.omniesoft.commerce.owner.service.service.ServiceScopeTimeSheetService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServicePriceEntity;
import com.omniesoft.commerce.persistence.repository.order.OrderRepository;
import com.omniesoft.commerce.persistence.repository.service.ServicePriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceScopeTimeSheetServiceImpl implements ServiceScopeTimeSheetService {

    private final ServicePriceRepository servicePriceRepository;

    private final ServicePriceConverter converter;

    private final ServiceScopeCrudService serviceScopeCrudService;

    private final OrderRepository orderRepository;

    private final ServicePriceTimeSettingValidator comparator;


    @Override
    public Map<DayOfWeek, List<ServicePricePayload>> getServiceTimeSheet(UUID service, UUID org,
                                                                         UserEntity userEntity) {

        List<ServicePriceEntity> servicePriceEntities = servicePriceRepository
                .findLastAvailableByServiceId(service, org, LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        return converter.toTimeSheet(servicePriceEntities);
    }


    @Override
    @Transactional
    public void mergeDaysPrices(DayOfWeek day, List<ServicePricePayload> pricePayloads, UUID service, UUID org,
                                UserEntity userEntity) {


        LocalDateTime lastDateOfOrderForService = orderRepository.findLastDateOfOrderForService(service);
        ServiceEntity one = serviceScopeCrudService.find(service);

        comparator.validate(pricePayloads);
        //Getting available service time-sheet for service, day and available date
        List<ServicePriceEntity> allByServiceIdAndDay = getServicePriceForDay(day, service,
                lastDateOfOrderForService);
        if (isReplaceableTimeSheet(day, allByServiceIdAndDay)) {
            servicePriceRepository.delete(allByServiceIdAndDay);
        }

        List<ServicePriceEntity> servicePriceEntities = prepareServicePrices(pricePayloads, day,
                lastDateOfOrderForService, one,
                userEntity);

        servicePriceRepository.save(servicePriceEntities);


    }

    @Override
    public void deleteDaysPrices(DayOfWeek day, UUID service, UUID org, UserEntity userEntity) {

        LocalDateTime lastDateOfOrderForService = orderRepository.findLastDateOfOrderForService(service);
        if (!lastDateOfOrderForService.isBefore(LocalDateTime.now())) {
            throw new UsefulException("You cant delete service price settings as order witch using this settings is exist", OwnerModuleErrorCodes.ACTION_NOT_ALLOWED_ORDER_IS_EXIST_BY_TIME_SHEET);
        }
        ServiceEntity one = serviceScopeCrudService.find(service);
        List<ServicePriceEntity> allByServiceIdAndDay = getServicePriceForDay(day, one.getId(),
                lastDateOfOrderForService);
        List<ServicePriceEntity> deletableServicePrice = allByServiceIdAndDay.stream().filter(e -> e.getDay() == day).collect(Collectors.toList());
        servicePriceRepository.delete(deletableServicePrice);

    }

    private List<ServicePriceEntity> getServicePriceForDay(DayOfWeek day, UUID service, LocalDateTime activeFrom) {

        return servicePriceRepository
                .findAllByServiceIdAndDay(service, day, activeFrom);
    }


    private boolean isReplaceableTimeSheet(DayOfWeek day, List<ServicePriceEntity> allByServiceIdAndDay) {

        return allByServiceIdAndDay != null && !allByServiceIdAndDay.isEmpty() && isSameAvailableDate
                (allByServiceIdAndDay, day);
    }

    private boolean isSameAvailableDate(List<ServicePriceEntity> allByServiceIdAndDay, DayOfWeek day) {

        for (ServicePriceEntity servicePriceEntity : allByServiceIdAndDay) {
            if (servicePriceEntity.getDay() != day) {
                return false;
            }
        }
        return true;
    }

    private List<ServicePriceEntity> prepareServicePrices(List<ServicePricePayload> servicePricePayloads,
                                                          DayOfWeek day,
                                                          LocalDateTime activeFrom, ServiceEntity serviceEntity,
                                                          UserEntity userEntity) {

        List<ServicePriceEntity> servicePriceEntities = Lists.newArrayList();
        servicePricePayloads.forEach(servicePricePayload -> {
            ServicePriceEntity servicePriceEntity = prepareServicePriceEntity(day, activeFrom, serviceEntity,
                    userEntity, servicePricePayload);
            servicePriceEntities.add(servicePriceEntity);
        });
        return servicePriceEntities;
    }

    private ServicePriceEntity prepareServicePriceEntity(DayOfWeek day, LocalDateTime activeFrom,
                                                         ServiceEntity serviceEntity,
                                                         UserEntity userEntity,
                                                         ServicePricePayload servicePricePayload) {
        ServicePriceEntity servicePriceEntity = new ServicePriceEntity();
        servicePriceEntity.setDay(day);
        servicePriceEntity.setService(serviceEntity);
        servicePriceEntity.setActiveFrom(activeFrom);
        servicePriceEntity.setCreateTime(LocalDateTime.now());

        servicePriceEntity.setPrice(servicePricePayload.getPrice());
        servicePriceEntity.setExpense(servicePricePayload.getExpense());
        servicePriceEntity.setPriceUnit(servicePricePayload.getPriceUnit());

        servicePriceEntity.setUpdateByUser(userEntity);
        servicePriceEntity.setStart(servicePricePayload.getStart());
        servicePriceEntity.setEnd(servicePricePayload.getEnd());
        return servicePriceEntity;
    }

}
