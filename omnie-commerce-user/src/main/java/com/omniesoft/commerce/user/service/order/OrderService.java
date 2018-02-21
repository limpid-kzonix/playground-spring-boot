package com.omniesoft.commerce.user.service.order;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.order.OrderUserSummary;
import com.omniesoft.commerce.user.controller.order.payload.OrderDetailsDto;
import com.omniesoft.commerce.user.controller.order.payload.OrderPriceDto;
import com.omniesoft.commerce.user.controller.order.payload.SaveOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.12.17
 */
public interface OrderService {
    Timesheet createTimesheet(UUID serviceId, LocalDate date);

    UUID makeOrder(SaveOrderDto order, UserEntity user);

    OrderPriceDto orderPrice(SaveOrderDto order, UserEntity user);

    OrderDetailsDto getOrderDetails(UUID serviceId, UUID orderId);

    void confirmOrder(UUID serviceId, UUID orderId, UserEntity user);

    void cancelOrder(UUID serviceId, UUID orderId, UserEntity user);

    List<LocalDate> fetchDateWithOrdersByStartDateAndEndDateAndUser(LocalDate startDate, LocalDate endDate, UserEntity userEntity);

    Page<OrderUserSummary> fetchOrdersByDateAndUser(LocalDate date, UserEntity userEntity, Pageable pageable);


}
