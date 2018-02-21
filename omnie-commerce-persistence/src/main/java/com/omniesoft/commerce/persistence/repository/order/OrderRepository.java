package com.omniesoft.commerce.persistence.repository.order;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.projection.order.OrderUserSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<OrderEntity, UUID>, OrderRepositoryCustom {
    List<OrderEntity> findAllByUserIdAndStatus(UUID userId, OrderStatus status);

    List<OrderEntity> findAllByServiceOrganizationIdAndStatus(UUID organization, OrderStatus status);

    OrderEntity findByServiceIdAndUserAndStatus(UUID serviceId, UserEntity userEntity, OrderStatus status);

    OrderEntity findByServiceOrganizationIdAndUserAndStatus(UUID organizationId, UserEntity userEntity, OrderStatus
            status);

    OrderEntity findByIdAndService_Id(UUID id, UUID serviceId);

    Page<OrderUserSummary> findAllByUserIdAndStartBetween(UUID uuid, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
