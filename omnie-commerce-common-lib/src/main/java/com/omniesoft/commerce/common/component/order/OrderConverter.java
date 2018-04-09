package com.omniesoft.commerce.common.component.order;

import com.omniesoft.commerce.common.component.order.dto.SaveFullOrderSubServices;
import com.omniesoft.commerce.common.component.order.dto.SaveOrderSubServices;
import com.omniesoft.commerce.common.component.order.dto.order.OrderDto;
import com.omniesoft.commerce.common.component.order.dto.order.OrderWithPricesDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderFullPriceDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderPriceDto;
import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderStoryEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;

import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 22.11.17
 */
public interface OrderConverter {
    List<OrderSubService> transformOrderSubServices(List<OrderSubServicesEntity> subServices);

    List<OrderSubService> transformSaveOrderSubServices(List<SaveOrderSubServices> subServices);

    List<OrderSubService> mapSaveFullOrderSubServices(List<SaveFullOrderSubServices> subServices);

    OrderDto mapToOrderDto(OrderEntity orderEntity);

    OrderWithPricesDto mapOrderPricesDto(OrderEntity orderEntity);

    OrderPriceDto mapToPriceDto(OrderEntity orderEntity);

    OrderFullPriceDto mapToFullPriceDto(OrderEntity orderEntity);

    OrderStoryEntity mapToStory(OrderEntity order);
}
