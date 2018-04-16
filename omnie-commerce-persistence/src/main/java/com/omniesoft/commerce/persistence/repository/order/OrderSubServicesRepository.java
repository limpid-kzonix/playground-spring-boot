package com.omniesoft.commerce.persistence.repository.order;

import com.omniesoft.commerce.persistence.entity.order.OrderSubServicesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderSubServicesRepository extends CrudRepository<OrderSubServicesEntity, UUID> {
}
