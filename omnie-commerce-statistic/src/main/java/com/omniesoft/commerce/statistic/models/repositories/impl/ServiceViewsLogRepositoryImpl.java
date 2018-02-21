package com.omniesoft.commerce.statistic.models.repositories.impl;

import com.omniesoft.commerce.statistic.models.repositories.ServiceViewsLogRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ServiceViewsLogRepositoryImpl implements ServiceViewsLogRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void update() {

    }
}
