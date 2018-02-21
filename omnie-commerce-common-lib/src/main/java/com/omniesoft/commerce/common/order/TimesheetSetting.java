package com.omniesoft.commerce.common.order;

import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;

/**
 * @author Vitalii Martynovskyi
 * @since 30.10.17
 */
public interface TimesheetSetting {
    ServiceEntity getService();

    Boolean getMinDuration();

    Long getDurationMillis();

    Long getPauseMillis();

    Integer getSlotCount();

    Double getMaxDiscount();

}
