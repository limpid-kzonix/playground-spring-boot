package com.omniesoft.commerce.common.component.order;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;

/**
 * @author Vitalii Martynovskyi
 * @since 19.12.17
 */
public interface OrderPriceService {
    void calculatePrice(OrderEntity orderEntity, Timesheet timesheet);
}
