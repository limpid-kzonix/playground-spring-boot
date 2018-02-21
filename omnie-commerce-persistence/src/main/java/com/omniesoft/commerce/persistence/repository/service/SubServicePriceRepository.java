package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.service.SubServicePriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
public interface SubServicePriceRepository extends CrudRepository<SubServicePriceEntity, UUID> {


    @Query("" +
            "select " +
            "   ssp " +
            "from SubServicePriceEntity ssp " +
            "left join ssp.subService ss " +
            "where ss.id = :subService " +
            "and ssp.activeFrom = (" +
            "select " +
            "   max(ssp1.activeFrom) " +
            "from SubServicePriceEntity ssp1 " +
            "left join ssp1.subService ss1 " +
            "where ss1.id = :subService " +
            "and ssp.activeFrom <= :date " +
            ")"
    )
    SubServicePriceEntity findBySubServiceIdAndActiveFrom(@Param("subService") UUID subServiceId,
                                                          @Param("date") LocalDateTime now);
}
