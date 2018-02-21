package com.omniesoft.commerce.owner.service.order;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.owner.controller.order.payload.OrderDetailsDto;
import com.omniesoft.commerce.owner.controller.order.payload.OrderPriceDto;
import com.omniesoft.commerce.owner.controller.order.payload.SaveOrderDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
public interface OrderService {
    UUID makeOrder(SaveOrderDto order, UserEntity admin);

    Timesheet createTimesheetForAdministrator(UUID serviceId, LocalDate date);

    Timesheet createHorizontalTimesheetForAdministrator(UUID serviceId, LocalDate date);

    void editOrder(UUID orderId, SaveOrderDto order, UserEntity admin);

    void cancelOrder(UUID serviceId, UUID orderId, UserEntity admin);

    void confirmOrder(UUID serviceId, UUID orderId, UserEntity admin);

    void doneOrder(UUID serviceId, UUID orderId, UserEntity admin);

    void failOrder(UUID serviceId, UUID orderId, UserEntity admin);

    OrderDetailsDto getOrderDetails(UUID serviceId, UUID orderId);

    OrderPriceDto orderPrice(SaveOrderDto order, UserEntity admin);

}

