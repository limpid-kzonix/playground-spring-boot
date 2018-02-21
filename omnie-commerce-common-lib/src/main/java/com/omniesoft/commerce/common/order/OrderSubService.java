package com.omniesoft.commerce.common.order;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 02.11.17
 */
public interface OrderSubService {
    UUID getSubServiceId();

    Integer getCount();

    Long getDuration();

    Double getDiscountPercent();

    Double getSubServicePrice();

    Double getSubServiceExpense();

    Double getTotalPrice();

}
