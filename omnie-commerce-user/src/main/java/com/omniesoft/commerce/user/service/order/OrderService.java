package com.omniesoft.commerce.user.service.order;

import com.omniesoft.commerce.common.component.order.dto.SaveOrderDto;
import com.omniesoft.commerce.common.component.order.dto.order.OrderDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderPriceDto;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.order.OrderUserSummary;
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
    Timesheet createTimesheet(final UUID serviceId, final LocalDate date);

    UUID makeOrder(SaveOrderDto order, UserEntity user);

    OrderPriceDto orderPrice(SaveOrderDto order, UserEntity user);

    OrderDto getOrderDetails(final UUID serviceId, final UUID orderId);

    void confirmOrder(final UUID serviceId, final UUID orderId, UserEntity user);

    void cancelOrder(final UUID serviceId, final UUID orderId, UserEntity user);

    List<LocalDate> fetchDateWithOrdersByStartDateAndEndDateAndUser(final LocalDate startDate, final LocalDate endDate, UserEntity userEntity);

    Page<OrderUserSummary> fetchOrdersByDateAndUser(final LocalDate date, UserEntity userEntity, Pageable pageable);


}
