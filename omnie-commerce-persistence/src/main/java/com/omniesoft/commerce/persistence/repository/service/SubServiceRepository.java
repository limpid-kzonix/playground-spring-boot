package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 13.07.17
 */
@Transactional
public interface SubServiceRepository extends PagingAndSortingRepository<SubServiceEntity, UUID>, SubServiceRepositoryCustom {

    @Query(value = "" +
            "select " +
            "   ss " +
            "from SubServiceEntity ss " +
            "left join ss.service s " +
            "where s.id = :service " +
            "and s.deleteStatus = false ",
            countQuery = "select " +
                    "   ss " +
                    "from SubServiceEntity ss " +
                    "left join ss.service s " +
                    "where s.id = :service and s.deleteStatus = false "
    )
    Page<SubServiceEntity> findByServiceId(@Param("service") UUID uuid, Pageable pageable);


}

