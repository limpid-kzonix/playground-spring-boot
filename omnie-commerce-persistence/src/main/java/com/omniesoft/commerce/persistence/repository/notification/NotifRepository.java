package com.omniesoft.commerce.persistence.repository.notification;

import com.omniesoft.commerce.persistence.entity.notification.NotifEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface NotifRepository extends PagingAndSortingRepository<NotifEntity, UUID> {
}
