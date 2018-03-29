package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.service.ServiceTimingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ServiceTimingRepository extends CrudRepository<ServiceTimingEntity, UUID> {

    // TODO: 28.03.18
    @Query("select" +
            "   sts " +
            "from ServiceTimingEntity sts " +
            "left join sts.service s " +
            "where sts.activeFrom = (" +
            "                       select " +
            "                           max(sts1.activeFrom) " +
            "                       from   ServiceTimingEntity sts1 " +
            "                       left join sts1.service s1 " +
            "                       where " +
            "                           sts1.activeFrom <= :date" +
            "                           and s1.id = :service " +
            "                       " +
            "                       )  " +
            "   and s.id = :service")
    ServiceTimingEntity findByServiceId(@Param("service") UUID serviceId, @Param("date") LocalDateTime activeFrom);


}
