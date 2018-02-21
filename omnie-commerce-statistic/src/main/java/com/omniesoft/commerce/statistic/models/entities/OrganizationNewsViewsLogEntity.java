package com.omniesoft.commerce.statistic.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "news_views_log")
public class OrganizationNewsViewsLogEntity {

    @Id
    @Field(value = "_id")
    private String id;
    @Field(value = "user_id")
    private String userId;

    @Field(value = "news_id")
    private String newsId;

    @Field(value = "date_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dateTime;

}
