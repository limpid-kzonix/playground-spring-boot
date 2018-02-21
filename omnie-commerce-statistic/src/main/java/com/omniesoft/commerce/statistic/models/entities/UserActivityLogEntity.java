package com.omniesoft.commerce.statistic.models.entities;

import com.omniesoft.commerce.statistic.models.enums.UserActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users_activity_log")
public class UserActivityLogEntity {

    @Id
    @Field(value = "_id")
    private String id;

    @Field(value = "user_id")
    private String userId;

    @Field(value = "type")
    @Enumerated(EnumType.STRING)
    private UserActionType type;

    @Field(value = "date_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dateTime;

}
