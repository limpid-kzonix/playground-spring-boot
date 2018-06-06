package com.omniesoft.commerce.persistence.repository.order;

import com.omniesoft.commerce.persistence.entity.order.OrderEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
public interface OrderRepositoryCustom {
    List<OrderEntity> findReadyToProcessing(LocalDateTime start, LocalDateTime end, UUID serviceId);

    List<OrderEntity> findAccepted(LocalDateTime start, LocalDateTime end, UUID serviceId);

    List<OrderEntity> findConfictedForUser(LocalDateTime start, LocalDateTime end, UUID serviceId);

    OrderEntity findByIdAndServiceIdGraph(UUID id, UUID serviceId, String graphName);

    LocalDateTime findLastDateOfOrderForService(UUID service);

    void deleteSubServiceOrders(OrderEntity order);

    List<LocalDateTime> findAllDayDateWithOrders(UUID userId, LocalDate startDate, LocalDate endDate);
}
