package com.omniesoft.commerce.statistic.models.repositories;

import com.omniesoft.commerce.statistic.models.entities.UserActivityLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Transactional
public interface UserActivityLogRepository extends MongoRepository<UserActivityLogEntity, String> {

}
