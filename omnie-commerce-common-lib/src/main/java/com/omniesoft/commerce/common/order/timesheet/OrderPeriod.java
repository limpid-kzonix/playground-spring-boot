package com.omniesoft.commerce.common.order.timesheet;

import com.omniesoft.commerce.common.order.OrderSubService;
import com.omniesoft.commerce.common.order.PeriodValidator;
import com.omniesoft.commerce.common.order.TimesheetPeriod;
import com.omniesoft.commerce.common.order.TimesheetPeriodType;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 27.11.2017
 */
public class OrderPeriod implements TimesheetPeriod, Comparable {
    private static final TimesheetPeriodType TYPE = TimesheetPeriodType.ORDER;
    @Getter
    private LocalDateTime start;
    @Getter
    private LocalDateTime end, realEnd;
    @Getter
    private UUID id;
    @Getter
    private OrderStatus status;
    @Getter
    private UUID customerId;
    @Getter
    private String customerName;
    @Getter
    private List<OrderSubService> subServices;

    private OrderPeriod(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    OrderPeriod(LocalDateTime start,
                LocalDateTime end,
                LocalDateTime realEnd,
                OrderStatus status,
                UUID id,
                UUID customerId,
                String customerName,
                List<OrderSubService> subServices) {
        this.start = start;
        this.end = end;
        this.realEnd = realEnd;
        this.status = status;
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.subServices = subServices;
    }

    static OrderPeriod of(LocalDateTime start,
                          LocalDateTime end,
                          LocalDateTime realEnd,
                          OrderStatus status,
                          UUID orderId,
                          UUID customerId,
                          String customerName,
                          List<OrderSubService> subServices) throws IllegalArgumentException {

        PeriodValidator.timesheetPeriod(start, end);

        return new OrderPeriod(start, end, realEnd, status, orderId, customerId, customerName, subServices);
    }

    @Override
    public TimesheetPeriodType getType() {
        return TYPE;
    }

    @Override
    public int compareTo(Object o) {
        TimesheetPeriod other = (TimesheetPeriod) o;
        return this.getStart().compareTo(other.getStart());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPeriod that = (OrderPeriod) o;
        return Objects.equals(start, that.start) &&
                Objects.equals(end, that.end) &&
                Objects.equals(realEnd, that.realEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, realEnd);
    }

    @Override
    public String toString() {
        return "OrderPeriod{" +
                "\n start        =" + start +
                ",\n end          =" + end +
                ",\n realEnd      =" + realEnd +
                ",\n id           =" + id +
                ",\n status       =" + status +
                ",\n customerId   =" + customerId +
                ",\n customerName ='" + customerName + '\'' +
                ",\n subServices  =" + subServices +
                "}\n";
    }
}