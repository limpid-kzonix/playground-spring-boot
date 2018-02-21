package com.omniesoft.commerce.owner.service.service;

import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;

import java.util.UUID;

public interface ServiceScopeCrudService {

    ServiceEntity find(UUID service);


}
