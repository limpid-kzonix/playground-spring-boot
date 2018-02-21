package com.omniesoft.commerce.user.service.favorite;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationFavoriteSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceFavoriteSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FavoriteService {

    Page<OrganizationFavoriteSummary> getOrganizationFavorites(UserEntity userEntity,
                                                               Pageable pageable);

    void deleteOrganizationFromFavorites(UUID orgId, UserEntity userEntity);

    void addOrganizationToFavorites(UUID orgId, UserEntity userEntity);


    Page<ServiceFavoriteSummary> getServiceFavorites(UserEntity userEntity,
                                                     Pageable pageable);

    void deleteServiceFromFavorites(UUID serviceId, UserEntity userEntity);

    void addServiceToFavorites(UUID serviceId, UserEntity userEntity);
}
