package com.omniesoft.commerce.persistence.docs.documents.notification;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "notifications")
public class NotificationDoc {
    @Id
    private String id;

    private UUID receiver;

    private OrderNotification orderNotification;

    private String type;

    private String target;

    private LocalDateTime createTime;

}
