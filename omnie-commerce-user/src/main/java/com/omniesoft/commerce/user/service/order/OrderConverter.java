package com.omniesoft.commerce.user.service.order;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.user.controller.order.payload.OrderDetailsDto;
import com.omniesoft.commerce.user.controller.order.payload.OrderPriceDto;
import com.omniesoft.commerce.user.controller.order.payload.SaveOrderSubServices;

import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 22.11.17
 */
public interface OrderConverter {
    List<OrderSubService> transformOrderSubServices(List<OrderSubServicesEntity> subServices);

    List<OrderSubService> transformSaveOrderSubServices(List<SaveOrderSubServices> subServices);

    OrderDetailsDto transformToOrderDetails(OrderEntity orderEntity);

    OrderPriceDto transformEntityToPriceDto(OrderEntity orderEntity);
}
