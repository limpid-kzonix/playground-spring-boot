package com.omniesoft.commerce.user.service.favorite.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationFavoriteEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationFavoriteKey;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceFavoriteEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceFavoriteKey;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationFavoriteSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceFavoriteSummary;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationFavoriteRepository;
import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceFavoriteRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import com.omniesoft.commerce.user.service.favorite.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private ServiceFavoriteRepository serviceFavoriteRepository;

    private ServiceRepository serviceRepository;

    private OrganizationRepository organizationRepository;

    private OrganizationFavoriteRepository organizationFavoriteRepository;

    @Override
    public Page<OrganizationFavoriteSummary> getOrganizationFavorites(UserEntity userEntity, Pageable pageable) {

        Page<OrganizationFavoriteSummary> allByUser = organizationFavoriteRepository
                .findAllByUser(userEntity, pageable);

        return allByUser;
    }

    @Override
    public void deleteOrganizationFromFavorites(UUID orgId, UserEntity userEntity) {

        OrganizationEntity organizationEntity = getOrganizationEntity(orgId);
        organizationFavoriteRepository.delete(new OrganizationFavoriteKey(userEntity, organizationEntity));
    }

    private OrganizationEntity getOrganizationEntity(UUID orgId) {

        OrganizationEntity one = organizationRepository.findOne(orgId);
        if (one == null) {
            throw new UsefulException(orgId.toString());
        }
        return one;
    }

    @Override
    public void addOrganizationToFavorites(UUID orgId, UserEntity userEntity) {

        OrganizationEntity organizationEntity = getOrganizationEntity(orgId);
        OrganizationFavoriteEntity organizationFavoriteEntity = new OrganizationFavoriteEntity();
        OrganizationFavoriteKey organizationFavoriteKey = new OrganizationFavoriteKey(userEntity, organizationEntity);
        organizationFavoriteEntity.setId(organizationFavoriteKey);
        organizationFavoriteRepository.save(organizationFavoriteEntity);
    }

    @Override
    public Page<ServiceFavoriteSummary> getServiceFavorites(UserEntity userEntity,
                                                            Pageable pageable) {

        Page<ServiceFavoriteSummary> allByIdUser = serviceFavoriteRepository.findAllByIdUser(userEntity, pageable);
        return allByIdUser;
    }


    @Override
    public void deleteServiceFromFavorites(UUID serviceId, UserEntity userEntity) {

        ServiceEntity serviceEntity = getServiceEntity(serviceId);
        ServiceFavoriteKey serviceFavoriteKey = new ServiceFavoriteKey(userEntity, serviceEntity);
        serviceFavoriteRepository.delete(serviceFavoriteKey);
    }


    @Override
    public void addServiceToFavorites(UUID serviceId, UserEntity userEntity) {


        ServiceEntity serviceEntity = getServiceEntity(serviceId);
        ServiceFavoriteKey serviceFavoriteKey = new ServiceFavoriteKey(userEntity, serviceEntity);
        ServiceFavoriteEntity serviceFavoriteEntity = new ServiceFavoriteEntity();
        serviceFavoriteEntity.setId(serviceFavoriteKey);

        checkAndAddOrganizationToFavorite(serviceEntity, userEntity);
        serviceFavoriteRepository.save(serviceFavoriteEntity);
    }

    private void checkAndAddOrganizationToFavorite(ServiceEntity serviceEntity,
                                                   UserEntity userEntity) {

        OrganizationEntity organization = serviceEntity.getOrganization();
        OrganizationFavoriteEntity byIdOrganization = organizationFavoriteRepository
                .findByIdOrganizationAndIdUser(organization, userEntity);
        if (byIdOrganization == null) {
            addOrganizationToFavorites(organization.getId(), userEntity);
        }
    }

    private ServiceEntity getServiceEntity(UUID serviceId) {

        ServiceEntity one = serviceRepository.findOne(serviceId);
        if (one == null) {
            throw new UsefulException(serviceId.toString());
        }
        return one;
    }
}
