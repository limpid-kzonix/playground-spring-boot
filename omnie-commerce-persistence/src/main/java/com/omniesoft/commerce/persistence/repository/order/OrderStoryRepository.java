package com.omniesoft.commerce.persistence.repository.order;

import com.omniesoft.commerce.persistence.entity.order.OrderStoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderStoryRepository extends CrudRepository<OrderStoryEntity, UUID> {

}
