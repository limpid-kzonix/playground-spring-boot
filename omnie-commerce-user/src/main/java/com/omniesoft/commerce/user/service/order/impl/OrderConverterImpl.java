package com.omniesoft.commerce.user.service.order.impl;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.user.controller.order.payload.*;
import com.omniesoft.commerce.user.service.order.OrderConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 22.11.17
 */
@Service
@RequiredArgsConstructor
public class OrderConverterImpl implements OrderConverter {

    @Override
    public List<OrderSubService> transformOrderSubServices(List<OrderSubServicesEntity> subServices) {

        List<OrderSubService> result = new ArrayList<>();
        if (subServices != null) {
            for (OrderSubServicesEntity subService : subServices) {
                result.add(new SubServiceEntityAdapter(subService));
            }
        }
        return result;
    }

    @Override
    public List<OrderSubService> transformSaveOrderSubServices(List<SaveOrderSubServices> subServices) {
        List<OrderSubService> result = new ArrayList<>();
        if (subServices != null) {
            for (SaveOrderSubServices subService : subServices) {
                result.add(new SaveSubServiceAdapter(subService));
            }
        }
        return result;
    }

    @Override
    public OrderDetailsDto transformToOrderDetails(OrderEntity orderEntity) {
        OrderDetailsDto result = new OrderDetailsDto();

        result.setId(orderEntity.getId());
        result.setNumber(orderEntity.getNumber());
        result.setStart(orderEntity.getStart());
        result.setEnd(orderEntity.getEnd());
        result.setStatus(orderEntity.getStatus());
        result.setCustomerName(orderEntity.getCustomerName());
        result.setCustomerPhone(orderEntity.getCustomerPhone());
        result.setComment(orderEntity.getComment());
        result.setTotalPrice(orderEntity.getTotalPrice());
        result.setDiscountPercent(orderEntity.getDiscountPercent());
        result.setCreateTime(orderEntity.getCreateTime());

        result.setSubServices(new ArrayList<>());
        for (OrderSubServicesEntity subServicesEntity : orderEntity.getSubServices()) {
            OrderSubServicesDetailsDto subServicesDetails = new OrderSubServicesDetailsDto();

            subServicesDetails.setId(subServicesEntity.getId());
            subServicesDetails.setSubServiceId(subServicesEntity.getSubService().getId());
            subServicesDetails.setCount(subServicesEntity.getCount());
            subServicesDetails.setDuration(subServicesEntity.getDuration());
            subServicesDetails.setDiscountPercent(subServicesEntity.getDiscountPercent());
            subServicesDetails.setTotalPrice(subServicesEntity.getTotalPrice());

            result.getSubServices().add(subServicesDetails);
        }

        return result;
    }

    @Override
    public OrderPriceDto transformEntityToPriceDto(OrderEntity orderEntity) {
        OrderPriceDto price = new OrderPriceDto();
        price.setDiscountPercent(orderEntity.getDiscountPercent());
        price.setTotalPrice(orderEntity.getTotalPrice());
        price.setSubServices(new ArrayList<>());

        for (OrderSubServicesEntity orderSubServicesEntity : orderEntity.getSubServices()) {
            OrderSubServicePriceDto ssPrice = new OrderSubServicePriceDto();

            ssPrice.setSubServiceId(orderSubServicesEntity.getSubService().getId());
            ssPrice.setCount(orderSubServicesEntity.getCount());
            ssPrice.setDiscountPercent(orderSubServicesEntity.getDiscountPercent());
            ssPrice.setSubServicePrice(orderSubServicesEntity.getSubServicePrice());
        }
        return price;
    }
}
