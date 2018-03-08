package com.omniesoft.commerce.common.subservice;

import com.omniesoft.commerce.common.converter.SubServicePriceConverter;
import com.omniesoft.commerce.common.converter.impl.SubServicePriceConverterImpl;
import com.omniesoft.commerce.common.payload.discount.DiscountPayload;
import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.persistence.entity.enums.MeasurementUnit;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServicePriceEntity;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Ignore
public class SubServicePriceTest {
    private static SubServicePriceConverter converter = new SubServicePriceConverterImpl();

    private final Logger log = LoggerFactory.getLogger(SubServicePriceTest.class);


    @Test
    public void discountPayload_equalsTest() {

        UUID uuid = UUID.randomUUID();
        DiscountPayload string = new DiscountPayload(uuid, "String", 0.1);
        DiscountPayload string1 = new DiscountPayload(uuid, "String", 0.1);
        Assert.assertEquals(string, string1);
    }

    @Test
    public void test() {

        ArrayList<SubServicePriceEntity> priceEntities = getSubServicePriceEntities();


        ArrayList<SubServiceEntity> subServiceEntities = getSubServiceEntities(priceEntities);
        log.info("\n\n + Input");
        subServiceEntities.forEach(e -> {
            e.getPrices().forEach(ne -> {
                log.info(e.getName() + ne.getActiveFrom() + "\n");
            });
        });

        log.info("\n\n + Converted");
        List<SubServicePayload> convert = converter.convert(subServiceEntities, LocalDateTime.of(2015, Month
                .NOVEMBER, 22, 16, 0));
        convert.forEach(e -> log.info(e.toString() + " \n"));
    }

    private ArrayList<SubServiceEntity> getSubServiceEntities(ArrayList<SubServicePriceEntity> priceEntities) {

        ArrayList<SubServiceEntity> subServiceEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SubServiceEntity subServiceEntity = new SubServiceEntity();
            subServiceEntity.setId(UUID.randomUUID());
            subServiceEntity.setName(RandomString.make());
            subServiceEntity.setPrices(priceEntities);
            subServiceEntities.add(subServiceEntity);
        }
        return subServiceEntities;
    }

    private ArrayList<SubServicePriceEntity> getSubServicePriceEntities() {

        ArrayList<SubServicePriceEntity> priceEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SubServicePriceEntity subServicePriceEntity = new SubServicePriceEntity();
            subServicePriceEntity.setActiveFrom(LocalDateTime.now().minus(i, ChronoUnit.YEARS));
            subServicePriceEntity.setPrice(100.00);
            subServicePriceEntity.setMinCount(10);
            subServicePriceEntity.setMaxCount(10);
            subServicePriceEntity.setMeasurementUnit(MeasurementUnit.DEGREES);
            subServicePriceEntity.setExpense(100.00);
            subServicePriceEntity.setDurationMillis((long) (60 * 60 * i + 1));
            subServicePriceEntity.setMaxDiscount(10.00);
            priceEntities.add(subServicePriceEntity);


        }
        return priceEntities;
    }
}
