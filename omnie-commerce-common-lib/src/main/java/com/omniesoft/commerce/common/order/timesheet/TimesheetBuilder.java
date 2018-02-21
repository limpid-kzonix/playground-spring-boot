package com.omniesoft.commerce.common.order.timesheet;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 26.10.17
 */
public interface TimesheetBuilder {


    OrderPeriod orderPeriod(LocalDateTime start,
                            LocalDateTime end,
                            OrderStatus status,
                            UUID orderId,
                            UUID customerId,
                            String customerName,
                            List<OrderSubService> subServices);

    boolean put(OrderPeriod order);

    int size();

    void putWithoutRules(OrderPeriod op);

//    Timesheet getTimesheet();
}
