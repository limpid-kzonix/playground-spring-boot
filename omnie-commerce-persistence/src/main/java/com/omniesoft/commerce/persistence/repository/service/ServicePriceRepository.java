package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.service.ServicePriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 14.07.17
 */
@Transactional
public interface ServicePriceRepository extends CrudRepository<ServicePriceEntity, UUID> {
    @Query(value = "" +
            "select " +
            "   sp " +
            "from ServicePriceEntity sp " +
            "   left join sp.service as service" +
            "   left join service.organization as org " +
            "where " +
            "   sp.activeFrom = " +
            "(select " +
            "   max(sp1.activeFrom) " +
            "from ServicePriceEntity sp1 " +
            "   left join sp1.service as service1 " +
            "where " +
            "   sp1.activeFrom <= :date " +
            "   and sp1.day = sp.day " +
            "   and service1.id = :service )" +
            "        " +
            " and " +
            "   service.id = :service " +
            " and " +
            "   org.id = :organization ")
    List<ServicePriceEntity> findLastAvailableByServiceId(
            @Param("service") UUID service,
            @Param("organization") UUID org,
            @Param("date") LocalDateTime activeFrom
    );

    @Query(value = "" +
            "select " +
            "   sp " +
            "from ServicePriceEntity sp " +
            "   left join sp.service as service " +
            "where " +
            "   sp.activeFrom = " +
            "(select " +
            "   max(sp1.activeFrom) " +
            "from ServicePriceEntity sp1 " +
            "   left join sp1.service as service1 " +
            "where " +
            "   sp1.activeFrom <= :date " +
            "   and service1.id = :service " +
            "   and sp1.day = :day )" +
            "        " +
            "" +
            "and " +
            "   service.id = :service and sp.day = :day")
    List<ServicePriceEntity> findAllByServiceIdAndDay(@Param("service") UUID service, @Param("day") DayOfWeek day, @Param("date") LocalDateTime activeFrom);

}
