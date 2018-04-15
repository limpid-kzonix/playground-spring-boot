package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationFavoriteEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationFavoriteKey;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationFavoriteSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrganizationFavoriteRepository
        extends PagingAndSortingRepository<OrganizationFavoriteEntity, OrganizationFavoriteKey> {
    @Query(value = "" +
            "select ofe from OrganizationFavoriteEntity ofe " +
            "left join fetch ofe.id.user user " +
            "left join fetch ofe.id.organization organization " +
            "where user.id = :user ",
            countQuery =
            "select count(ofe) from OrganizationFavoriteEntity ofe " +
            "left join ofe.id.user user " +
            "where user.id = :user ")
    Page<OrganizationFavoriteSummary> findAllByUserId(@Param("user") UUID user, Pageable pageable);


    @Query(value = "" +
            "select ofe from OrganizationFavoriteEntity ofe " +
            "left join fetch ofe.id.user user " +
            "left join fetch ofe.id.organization organization " +
            "where user = :user " +
            "", countQuery = "" +
            "select count(ofe) from OrganizationFavoriteEntity ofe " +
            "left join ofe.id.user user " +
            "where user = :user ")
    Page<OrganizationFavoriteSummary> findAllByUser(@Param("user") UserEntity user, Pageable pageable);

    @Query(value = "" +
            "select ofe from OrganizationFavoriteEntity ofe " +
            "left join fetch ofe.id.user user " +
            "where user = :user " +
            "", countQuery = "" +
            "select count(ofe) from OrganizationFavoriteEntity ofe " +
            "left join ofe.id.user user " +
            "where user = :user ")
    List<OrganizationFavoriteSummary> findAllByUser(@Param("user") UserEntity user);

    OrganizationFavoriteEntity findByIdOrganizationAndIdUser(OrganizationEntity organizationEntity, UserEntity user);
}
