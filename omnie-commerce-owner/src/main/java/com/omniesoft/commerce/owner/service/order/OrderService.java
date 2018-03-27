package com.omniesoft.commerce.owner.service.order;

import com.omniesoft.commerce.common.component.order.dto.SaveFullOrderDto;
import com.omniesoft.commerce.common.component.order.dto.order.OrderWithPricesDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderFullPriceDto;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
public interface OrderService {
    UUID makeOrder(SaveFullOrderDto order, UserEntity admin);

    Timesheet createTimesheetForAdministrator(UUID serviceId, LocalDate date);

    Timesheet createHorizontalTimesheetForAdministrator(UUID serviceId, LocalDate date);

    void editOrder(UUID orderId, SaveFullOrderDto order, UserEntity admin);

    void cancelOrder(UUID serviceId, UUID orderId, UserEntity admin);

    void confirmOrder(UUID serviceId, UUID orderId, UserEntity admin);

    void doneOrder(UUID serviceId, UUID orderId, UserEntity admin);

    void failOrder(UUID serviceId, UUID orderId, UserEntity admin);

    OrderWithPricesDto getOrderDetails(UUID serviceId, UUID orderId);

    OrderFullPriceDto orderPrice(SaveFullOrderDto order, UserEntity admin);

}

