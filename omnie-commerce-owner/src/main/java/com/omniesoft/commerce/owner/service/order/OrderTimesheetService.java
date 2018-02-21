package com.omniesoft.commerce.owner.service.order;

import com.omniesoft.commerce.common.order.Timesheet;
import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 21.11.17
 */
public interface OrderTimesheetService {
    Timesheet buildSingleDaySchedule(UUID serviceId, LocalDate scheduleDate, ServiceTimingEntity serviceTiming, Map<DayOfWeek, List<ScheduleSetting>> schedule);

    // put all orders
    void insertAllOrders(Timesheet timesheet, List<OrderEntity> orders);

    void insertAllOrdersWithoutRules(Timesheet timesheet, List<OrderEntity> orders);
}
