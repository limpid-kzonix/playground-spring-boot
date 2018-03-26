package com.omniesoft.commerce.user.controller.order;

import com.omniesoft.commerce.common.component.order.dto.SaveOrderDto;
import com.omniesoft.commerce.common.component.order.dto.order.AbstractOrderDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderPriceDto;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.order.OrderUserSummary;
import com.omniesoft.commerce.user.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
@RestController
@Validated
@Api(value = "Order", tags = "Order API", description = "@omnie46")
@RequiredArgsConstructor
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;


    @GetMapping(path = "services/{service-id}/orders/timesheet", produces = APPLICATION_JSON_VALUE)
    public Timesheet getServiceTimesheet(@PathVariable("service-id") UUID serviceId,
                                         @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                         @ApiIgnore UserEntity user) {

        return orderService.createTimesheet(serviceId, date);


    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "services/{service-id}/orders/timesheet", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseMessage.Created makeOrder(@PathVariable("service-id") UUID serviceId,
                                             @RequestBody @Valid SaveOrderDto order,
                                             @ApiIgnore UserEntity user) {

        order.setServiceId(serviceId);
        UUID id = orderService.makeOrder(order, user);
        return new ResponseMessage.Created(id);
    }

    @PostMapping(path = "services/{service-id}/orders/price", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public OrderPriceDto orderPrice(@PathVariable("service-id") UUID serviceId,
                                    @RequestBody @Valid SaveOrderDto order,
                                    @ApiIgnore UserEntity user) {

        order.setServiceId(serviceId);
        return orderService.orderPrice(order, user);

    }


    @GetMapping(path = "services/{service-id}/orders/{order-id}", produces = APPLICATION_JSON_VALUE)
    public AbstractOrderDto getOrderDetails(@PathVariable("service-id") UUID serviceId,
                                            @PathVariable("order-id") UUID orderId,
                                            @ApiIgnore UserEntity user) {

        return orderService.getOrderDetails(serviceId, orderId);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping(path = "services/{service-id}/orders/{order-id}/confirm")
    public void confirmOrder(@PathVariable("service-id") UUID serviceId,
                             @PathVariable("order-id") UUID orderId,
                             @ApiIgnore UserEntity user) {


        orderService.confirmOrder(serviceId, orderId, user);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping(path = "services/{service-id}/orders/{order-id}/cancel")
    public void cancelOrder(@PathVariable("service-id") UUID serviceId,
                            @PathVariable("order-id") UUID orderId,
                            @ApiIgnore UserEntity user) {
        orderService.cancelOrder(serviceId, orderId, user);
    }


    @GetMapping(path = "orders/date")
    public List<LocalDate> getListOfDateWithOrderByUser(
            @ApiIgnore UserEntity userEntity,
            @ApiParam(required = true, defaultValue = "2017-12-01")
            @RequestParam(required = true, name = "start_date") LocalDate startDate,
            @ApiParam(required = true, defaultValue = "2017-12-01")
            @RequestParam(required = true, name = "end_date") LocalDate endDate
    ) {
        log.debug(startDate.toString() + endDate.toString());
        return orderService.fetchDateWithOrdersByStartDateAndEndDateAndUser(startDate, endDate, userEntity);
    }

    @GetMapping(path = "orders")
    public Page<OrderUserSummary> getListOfOrdersByUser(
            @ApiIgnore UserEntity userEntity,
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiParam(required = true, defaultValue = "2017-12-01")
            @RequestParam(required = true, value = "selected_date") LocalDate selectedDate
    ) {
        return orderService.fetchOrdersByDateAndUser(selectedDate, userEntity, pageable);
    }

}
