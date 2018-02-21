package com.omniesoft.commerce.owner.converter.impl;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.owner.controller.order.payload.*;
import com.omniesoft.commerce.owner.converter.OrderConverter;
import com.omniesoft.commerce.owner.service.order.SaveSubServiceAdapter;
import com.omniesoft.commerce.owner.service.order.SubServiceEntityAdapter;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper mapper;

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
        result.setServicePrice(orderEntity.getServicePrice());
        result.setServiceExpense(orderEntity.getServiceExpense());
        result.setCreateTime(orderEntity.getCreateTime());
        result.setUpdateTime(orderEntity.getUpdateTime());

        result.setSubServices(new ArrayList<>());
        for (OrderSubServicesEntity subServicesEntity : orderEntity.getSubServices()) {
            OrderSubServicesDetailsDto subServicesDetails = new OrderSubServicesDetailsDto();

            subServicesDetails.setId(subServicesEntity.getId());
            subServicesDetails.setSubServiceId(subServicesEntity.getSubService().getId());
            subServicesDetails.setCount(subServicesEntity.getCount());
            subServicesDetails.setDuration(subServicesEntity.getDuration());
            subServicesDetails.setDiscountPercent(subServicesEntity.getDiscountPercent());
            subServicesDetails.setSubServicePrice(subServicesEntity.getSubServicePrice());
            subServicesDetails.setSubServiceExpense(subServicesEntity.getSubServiceExpense());
            subServicesDetails.setTotalPrice(subServicesEntity.getTotalPrice());

            result.getSubServices().add(subServicesDetails);
        }

        return result;
    }

    @Override
    public OrderPriceDto transformEntityToPriceDto(OrderEntity orderEntity) {
        OrderPriceDto price = new OrderPriceDto();
        price.setDiscountPercent(orderEntity.getDiscountPercent());
        price.setServicePrice(orderEntity.getServicePrice());
        price.setServiceExpense(orderEntity.getServiceExpense());
        price.setTotalPrice(orderEntity.getTotalPrice());
        price.setSubServices(new ArrayList<>());

        for (OrderSubServicesEntity orderSubServicesEntity : orderEntity.getSubServices()) {
            OrderSubServicePriceDto ssPrice = new OrderSubServicePriceDto();

            ssPrice.setSubServiceId(orderSubServicesEntity.getSubService().getId());
            ssPrice.setCount(orderSubServicesEntity.getCount());

            ssPrice.setDiscountPercent(orderSubServicesEntity.getDiscountPercent());
            ssPrice.setSubServicePrice(orderSubServicesEntity.getSubServicePrice());
            ssPrice.setSubServiceExpense(orderSubServicesEntity.getSubServiceExpense());
            ssPrice.setTotalPrice(orderSubServicesEntity.getTotalPrice());
        }
        return price;
    }
}
