package com.omniesoft.commerce.common.converter;

import com.omniesoft.commerce.common.payload.service.SubServicePayload;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface SubServicePriceConverter {
    List<SubServicePayload> convert(List<SubServiceEntity> subServiceEntities, LocalDateTime time);
}
