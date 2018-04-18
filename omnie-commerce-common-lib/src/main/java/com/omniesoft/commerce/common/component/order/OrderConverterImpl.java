package com.omniesoft.commerce.common.component.order;

import com.omniesoft.commerce.common.component.order.dto.*;
import com.omniesoft.commerce.common.component.order.dto.order.OrderDto;
import com.omniesoft.commerce.common.component.order.dto.order.OrderSubServicesDto;
import com.omniesoft.commerce.common.component.order.dto.order.OrderSubServicesWithPricesDto;
import com.omniesoft.commerce.common.component.order.dto.order.OrderWithPricesDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderFullPriceDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderPriceDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderSubServiceFullPriceDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderSubServicePriceDto;
import com.omniesoft.commerce.common.component.order.dto.story.OrderStoryDto;
import com.omniesoft.commerce.common.component.order.dto.story.OrderSubServicesStoryDto;
import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderStoryEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesStoryEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vitalii Martynovskyi
 * @since 22.11.17
 */
@Service
@RequiredArgsConstructor
public class OrderConverterImpl implements OrderConverter {

    @Override
    public List<OrderSubService> transformOrderSubServices(Set<OrderSubServicesEntity> subServices) {
        if (subServices != null) {
            return subServices.stream()
                    .map(SubServiceEntityAdapter::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }

    @Override
    public List<OrderSubService> transformSaveOrderSubServices(List<SaveOrderSubServices> subServices) {
        if (subServices != null) {
            return subServices.stream()
                    .map(SaveSubServiceAdapter::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }

    @Override
    public List<OrderSubService> mapSaveFullOrderSubServices(List<SaveFullOrderSubServices> subServices) {
        if (subServices != null) {
            return subServices.stream()
                    .map(SaveFullSubServiceAdapter::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }

    @Override
    public OrderDto mapToOrderDto(OrderEntity orderEntity, Optional<OrderStoryEntity> story) {
        OrderDto result = new OrderDto();

        result.setId(orderEntity.getId());
        result.setNumber(orderEntity.getNumber());
        result.setStart(orderEntity.getStart());
        result.setEnd(orderEntity.getEnd());
        result.setStatus(orderEntity.getStatus());
        result.setCustomerName(orderEntity.getCustomerName());
        result.setCustomerPhone(orderEntity.getCustomerPhone());
        result.setServiceId(orderEntity.getService().getId());
        result.setServiceName(orderEntity.getService().getName());
        result.setComment(orderEntity.getComment());
        result.setTotalPrice(orderEntity.getTotalPrice());
        result.setDiscountPercent(orderEntity.getDiscountPercent());
        result.setCreateTime(orderEntity.getCreateTime());
        result.setOrderStory(story.map(this::mapStory).orElse(null));

        result.setSubServices(new ArrayList<>());
        for (OrderSubServicesEntity subServicesEntity : SetUtils.emptyIfNull(orderEntity.getSubServices())) {
            OrderSubServicesDto subServicesDetails = new OrderSubServicesDto();

            subServicesDetails.setId(subServicesEntity.getId());
            subServicesDetails.setSubServiceId(subServicesEntity.getSubService().getId());
            subServicesDetails.setSubServiceName(subServicesEntity.getSubService().getName());
            subServicesDetails.setCount(subServicesEntity.getCount());
            subServicesDetails.setDuration(subServicesEntity.getDuration());
            subServicesDetails.setDiscountPercent(subServicesEntity.getDiscountPercent());
            subServicesDetails.setTotalPrice(subServicesEntity.getTotalPrice());

            result.getSubServices().add(subServicesDetails);
        }

        return result;
    }

    private OrderStoryDto mapStory(OrderStoryEntity story) {
        OrderStoryDto result = new OrderStoryDto();

        result.setStart(story.getStart());
        result.setEnd(story.getEnd());
        result.setStatus(story.getStatus());
        result.setCustomerName(story.getCustomerName());
        result.setCustomerPhone(story.getCustomerPhone());
        result.setServiceId(story.getService().getId());
        result.setServiceName(story.getService().getName());
        result.setComment(story.getComment());
        result.setTotalPrice(story.getTotalPrice());
        result.setDiscountPercent(story.getDiscountPercent());
        result.setCreateTime(story.getCreateTime());

        result.setSubServices(new ArrayList<>());
        for (OrderSubServicesStoryEntity subServicesStory : SetUtils.emptyIfNull(story.getSubServicesStory())) {
            OrderSubServicesStoryDto subServicesDetails = new OrderSubServicesStoryDto();
            subServicesDetails.setSubServiceId(subServicesStory.getSubService().getId());
            subServicesDetails.setSubServiceName(subServicesStory.getSubService().getName());
            subServicesDetails.setCount(subServicesStory.getCount());
            subServicesDetails.setDiscountPercent(subServicesStory.getDiscountPercent());
            subServicesDetails.setTotalPrice(subServicesStory.getTotalPrice());

            result.getSubServices().add(subServicesDetails);
        }
        return result;
    }

    @Override
    public OrderWithPricesDto mapOrderPricesDto(OrderEntity orderEntity) {
        OrderWithPricesDto result = new OrderWithPricesDto();

        result.setId(orderEntity.getId());
        result.setNumber(orderEntity.getNumber());
        result.setStart(orderEntity.getStart());
        result.setEnd(orderEntity.getEnd());
        result.setStatus(orderEntity.getStatus());
        result.setCustomerName(orderEntity.getCustomerName());
        result.setCustomerPhone(orderEntity.getCustomerPhone());
        result.setServiceId(orderEntity.getService().getId());
        result.setServiceName(orderEntity.getService().getName());
        result.setComment(orderEntity.getComment());
        result.setTotalPrice(orderEntity.getTotalPrice());
        result.setDiscountPercent(orderEntity.getDiscountPercent());
        result.setServicePrice(orderEntity.getServicePrice());
        result.setServiceExpense(orderEntity.getServiceExpense());
        result.setCreateTime(orderEntity.getCreateTime());
        result.setUpdateTime(orderEntity.getUpdateTime());

        result.setSubServices(new ArrayList<>());
        for (OrderSubServicesEntity subServicesEntity : SetUtils.emptyIfNull(orderEntity.getSubServices())) {
            OrderSubServicesWithPricesDto subServicesDetails = new OrderSubServicesWithPricesDto();

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
    public OrderPriceDto mapToPriceDto(OrderEntity orderEntity) {
        OrderPriceDto price = new OrderPriceDto();
        price.setDiscountPercent(orderEntity.getDiscountPercent());
        price.setTotalPrice(orderEntity.getTotalPrice());
        price.setSubServices(new ArrayList<>());

        for (OrderSubServicesEntity orderSubServicesEntity : SetUtils.emptyIfNull(orderEntity.getSubServices())) {
            OrderSubServicePriceDto ssPrice = new OrderSubServicePriceDto();

            ssPrice.setSubServiceId(orderSubServicesEntity.getSubService().getId());
            ssPrice.setCount(orderSubServicesEntity.getCount());
            ssPrice.setDiscountPercent(orderSubServicesEntity.getDiscountPercent());
            ssPrice.setTotalPrice(orderSubServicesEntity.getTotalPrice());

            price.getSubServices().add(ssPrice);
        }
        return price;
    }

    @Override
    public OrderFullPriceDto mapToFullPriceDto(OrderEntity orderEntity) {
        OrderFullPriceDto price = new OrderFullPriceDto();
        price.setDiscountPercent(orderEntity.getDiscountPercent());
        price.setServicePrice(orderEntity.getServicePrice());
        price.setServiceExpense(orderEntity.getServiceExpense());
        price.setTotalPrice(orderEntity.getTotalPrice());
        price.setSubServices(new ArrayList<>());

        for (OrderSubServicesEntity orderSubServicesEntity : SetUtils.emptyIfNull(orderEntity.getSubServices())) {
            OrderSubServiceFullPriceDto ssPrice = new OrderSubServiceFullPriceDto();

            ssPrice.setSubServiceId(orderSubServicesEntity.getSubService().getId());
            ssPrice.setCount(orderSubServicesEntity.getCount());
            ssPrice.setDiscountPercent(orderSubServicesEntity.getDiscountPercent());
            ssPrice.setSubServicePrice(orderSubServicesEntity.getSubServicePrice());
            ssPrice.setSubServiceExpense(orderSubServicesEntity.getSubServiceExpense());
            ssPrice.setTotalPrice(orderSubServicesEntity.getTotalPrice());

            price.getSubServices().add(ssPrice);
        }
        return price;
    }

    @Override
    public OrderStoryEntity mapToStory(OrderEntity order) {
        OrderStoryEntity story = new OrderStoryEntity();
        story.setOrder(order);
        story.setUser(order.getUser());
        story.setService(order.getService());
        story.setStatus(order.getStatus());
        story.setStart(order.getStart());
        story.setEnd(order.getEnd());
        story.setComment(order.getComment());
        story.setCustomerName(order.getCustomerName());
        story.setCustomerPhone(order.getCustomerPhone());
        story.setDiscount(order.getDiscount());
        story.setDiscountPercent(order.getDiscountPercent());
        story.setServicePrice(order.getServicePrice());
        story.setTotalPrice(order.getTotalPrice());

        Set<OrderSubServicesStoryEntity> ssStories = new HashSet<>();
        for (OrderSubServicesEntity subService : SetUtils.emptyIfNull(order.getSubServices())) {
            OrderSubServicesStoryEntity ssStory = new OrderSubServicesStoryEntity();
            ssStory.setSubService(subService.getSubService());
            ssStory.setOrderStory(story);
            ssStory.setDiscount(subService.getDiscount());
            ssStory.setDiscountPercent(subService.getDiscountPercent());
            ssStory.setCount(subService.getCount());
            ssStory.setSubServicePrice(subService.getSubServicePrice());
            ssStory.setTotalPrice(subService.getTotalPrice());

            ssStories.add(ssStory);
        }
        story.setSubServicesStory(ssStories);

        return story;
    }
}
