package com.omniesoft.commerce.persistence.docs.repository;

import com.omniesoft.commerce.persistence.docs.documents.notification.NotificationDoc;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface NotificationRepository extends MongoRepository<NotificationDoc, String> {


}
