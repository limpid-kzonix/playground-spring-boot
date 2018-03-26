package com.omniesoft.commerce.owner.controller.order;

import com.omniesoft.commerce.common.component.order.dto.SaveOrderDto;
import com.omniesoft.commerce.common.component.order.dto.order.OrderWithPricesDto;
import com.omniesoft.commerce.common.component.order.dto.price.OrderFullPriceDto;
import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.owner.controller.AbstractServiceController;
import com.omniesoft.commerce.owner.service.order.OrderService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.UUID;

import static com.omniesoft.commerce.persistence.entity.enums.AdminPermission.TIMESHEET_MANAGEMENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
@RestController
@Validated
@Api(value = "Order", tags = "Order API", description = "@omnie46")
@RequiredArgsConstructor
public class OrderController extends AbstractServiceController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final OwnerAccessControlService accessControl;


    @GetMapping(path = "{service-id}/timesheet", produces = APPLICATION_JSON_VALUE)
    public Timesheet getServiceTimesheet(@PathVariable("service-id") UUID serviceId,
                                         @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                         @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);

        return orderService.createTimesheetForAdministrator(serviceId, date);


    }

    @GetMapping(path = "{service-id}/timesheet-horizontal", produces = APPLICATION_JSON_VALUE)
    public Timesheet getServiceTimesheetHorizontal(@PathVariable("service-id") UUID serviceId,
                                                   @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                   @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);

        return orderService.createHorizontalTimesheetForAdministrator(serviceId, date);


    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{service-id}/timesheet", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseMessage.Created makeOrder(@PathVariable("service-id") UUID serviceId,
                                             @RequestBody @Valid SaveOrderDto order,
                                             @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);
        order.setServiceId(serviceId);
        UUID id = orderService.makeOrder(order, user);
        return new ResponseMessage.Created(id);
    }

    @PostMapping(path = "{service-id}/order-price", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public OrderFullPriceDto orderPrice(@PathVariable("service-id") UUID serviceId,
                                        @RequestBody @Valid SaveOrderDto order,
                                        @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);
        order.setServiceId(serviceId);
        return orderService.orderPrice(order, user);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(path = "{service-id}/order/{order-id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void editOrder(@PathVariable("service-id") UUID serviceId,
                          @PathVariable("order-id") UUID orderId,
                          @RequestBody @Valid SaveOrderDto order,
                          @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);
        order.setServiceId(serviceId);
        orderService.editOrder(orderId, order, user);

    }

    @GetMapping(path = "{service-id}/order/{order-id}", produces = APPLICATION_JSON_VALUE)
    public OrderWithPricesDto getOrderDetails(@PathVariable("service-id") UUID serviceId,
                                              @PathVariable("order-id") UUID orderId,
                                              @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);

        return orderService.getOrderDetails(serviceId, orderId);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping(path = "{service-id}/order/{order-id}/confirm")
    public void confirmOrder(@PathVariable("service-id") UUID serviceId,
                             @PathVariable("order-id") UUID orderId,
                             @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);

        orderService.confirmOrder(serviceId, orderId, user);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping(path = "{service-id}/order/{order-id}/done")
    public void doneOrder(@PathVariable("service-id") UUID serviceId,
                          @PathVariable("order-id") UUID orderId,
                          @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);

        orderService.doneOrder(serviceId, orderId, user);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping(path = "{service-id}/order/{order-id}/cancel")
    public void cancelOrder(@PathVariable("service-id") UUID serviceId,
                            @PathVariable("order-id") UUID orderId,
                            @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);

        orderService.cancelOrder(serviceId, orderId, user);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping(path = "{service-id}/order/{order-id}/fail")
    public void failOrder(@PathVariable("service-id") UUID serviceId,
                          @PathVariable("order-id") UUID orderId,
                          @ApiIgnore UserEntity user) {

        accessControl.hasPermissionByServiceId(user, serviceId, TIMESHEET_MANAGEMENT);

        orderService.failOrder(serviceId, orderId, user);

    }

}
