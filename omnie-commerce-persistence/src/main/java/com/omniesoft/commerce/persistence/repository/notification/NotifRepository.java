package com.omniesoft.commerce.persistence.repository.notification;

import com.omniesoft.commerce.persistence.entity.notification.NotifEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface NotifRepository extends PagingAndSortingRepository<NotifEntity, UUID> {

    @Query("select n from NotifEntity n where n.receiver=id and  n.target='ADMIN'")
    Page<NotifEntity> findAdminNotif(@Param("id") UUID id, Pageable pageable);

}
