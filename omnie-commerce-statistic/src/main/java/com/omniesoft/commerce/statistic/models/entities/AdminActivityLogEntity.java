package com.omniesoft.commerce.statistic.models.entities;

import com.omniesoft.commerce.statistic.models.enums.AdminActionType;
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
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "admin_activity_log")
public class AdminActivityLogEntity {

    @Id
    @Field(value = "_id")
    private String id;

    @Field(value = "admin_id")
    private String adminId;

    @Field(value = "organization_id")
    private String organizationId;

    @Field(value = "action_type")
    @Enumerated(EnumType.STRING)
    private AdminActionType type;

    @Field(value = "date_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dateTime;

}
