package com.omniesoft.commerce.owner;


import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.omniesoft.commerce.common.converter.ServicePriceConverter;
import com.omniesoft.commerce.common.converter.impl.ServicePriceConverterImpl;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.owner.service.service.ServicePriceTimeSettingValidator;
import com.omniesoft.commerce.owner.service.service.impl.ServicePriceTimeSettingValidatorImpl;
import com.omniesoft.commerce.persistence.entity.service.ServicePriceEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class ConverterTest {

    private ServicePriceTimeSettingValidator comparator = new ServicePriceTimeSettingValidatorImpl();

    private ServicePriceConverter serviceTimeSheetConverter = new ServicePriceConverterImpl(new ModelMapper());

    public static List<ServicePriceEntity> testData() {
        ArrayList<ServicePriceEntity> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < DayOfWeek.values().length - 1; j++) {
                ServicePriceEntity servicePriceEntity = new ServicePriceEntity();
                servicePriceEntity.setDay(DayOfWeek.values()[j]);
                servicePriceEntity.setPrice(10.0 * i);
                list.add(servicePriceEntity);
            }

        }
        return list;
    }


    @Test
    public void convertListToMultiMap() {
        List<ServicePriceEntity> servicePriceEntities = testData();
        ImmutableListMultimap<DayOfWeek, ServicePriceEntity> index = Multimaps.index(
                servicePriceEntities,
                s -> s.getDay()
        );
        ListMultimap<DayOfWeek, ServicePricePayload> dayServicePricePayloadListMultimap = Multimaps
                .transformValues(index, s -> serviceTimeSheetConverter.toServicePayload(s));
        Map<DayOfWeek, List<ServicePricePayload>> dayListMap = Multimaps.asMap(dayServicePricePayloadListMultimap);

        System.out.println("\n\n");
        dayListMap.forEach((k, v) -> {
            System.out.println("\n\n" +
                    k.name());
            v.forEach(e -> System.out.println(e));
        });
        System.out.println("\n\n");

    }

    @Test
    public void comparingPrice_Test1() {

        List<ServicePricePayload> servicePricePayloads = getServicePricePayloads();

        comparator.validate(servicePricePayloads);
    }

    @Test(expected = UsefulException.class)
    public void comparingPrice_Test2() {

        List<ServicePricePayload> servicePricePayloads = getServicePricePayloads();

        ServicePricePayload servicePricePayload = new ServicePricePayload();
        servicePricePayload.setStart(LocalTime.of(2, 2, 2));
        servicePricePayload.setEnd(LocalTime.of(23, 3, 3));
        servicePricePayloads.add(servicePricePayload);
        comparator.validate(servicePricePayloads);
    }

    private List<ServicePricePayload> getServicePricePayloads() {

        List<ServicePricePayload> servicePricePayloads = Lists.
                newArrayList();

        for (int i = 1; i < 10; i++) {
            ServicePricePayload servicePricePayload = new ServicePricePayload();
            servicePricePayload.setStart(LocalTime.of(i, 0, 0));
            servicePricePayload.setEnd(LocalTime.of(i + 1, 0, 0));
            System.out.println(servicePricePayload);
            servicePricePayloads.add(servicePricePayload);
        }
        return servicePricePayloads;
    }


}
