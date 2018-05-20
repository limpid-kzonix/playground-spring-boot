package com.omniesoft.commerce.common.ws.notification.payload;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserProfileEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;

public class EnhancedMessageFactory {


    public static User extract(UserEntity userEntity) {

        UserProfileEntity profile = userEntity.getProfile();

        User user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setLogin(userEntity.getLogin());
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        user.setImageId(profile.getImageId());

        return user;
    }

    public static Service extract(ServiceEntity serviceEntity) {

        Service service = new Service();

        service.setId(serviceEntity.getId());
        service.setLogoId(serviceEntity.getLogoId());
        service.setName(serviceEntity.getName());
        service.setOrganization(extract(serviceEntity.getOrganization()));

        return service;
    }

    public static Organization extract(OrganizationEntity organizationEntity) {

        Organization organization = new Organization();

        organization.setId(organizationEntity.getId());
        organization.setLogoId(organizationEntity.getLogoId());
        organization.setName(organizationEntity.getName());

        return organization;
    }
}
