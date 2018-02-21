package com.omniesoft.commerce.persistence.repository.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceFavoriteEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceFavoriteKey;
import com.omniesoft.commerce.persistence.projection.service.ServiceFavoriteSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface ServiceFavoriteRepository extends
        PagingAndSortingRepository<ServiceFavoriteEntity, ServiceFavoriteKey> {

    @Query(value = "" +
            "select sfe from ServiceFavoriteEntity sfe " +
            "left join fetch sfe.id.user user " +
            "left join fetch sfe.id.service service " +
            "where user.id = :user " +
            "", countQuery = "" +
            "select count(sfe) from ServiceFavoriteEntity sfe " +
            "left join sfe.id.user user " +
            "where user.id = :user ")
    Page<ServiceFavoriteSummary> findAllByIdUserId(@Param("user") UUID user, Pageable pageable);


    @Query(value = "" +
            "select sfe from ServiceFavoriteEntity sfe " +
            "left join fetch sfe.id.user user " +
            "left join fetch sfe.id.service service " +
            "where user = :user " +
            "", countQuery = "" +
            "select count(sfe) from ServiceFavoriteEntity sfe " +
            "left join sfe.id.user user " +
            "where user = :user ")
    Page<ServiceFavoriteSummary> findAllByIdUser(@Param("user") UserEntity user, Pageable pageable);
}
