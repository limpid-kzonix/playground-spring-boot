package com.omniesoft.commerce.statistic.models.entities;

import com.omniesoft.commerce.common.ws.statistic.impl.enums.FavoriteType;
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
@Document(collection = "organization_favorites_log")
public class OrganizationFavoritesLogEntity {
    @Id
    @Field(value = "_id")
    private String id;

    @Field(value = "user_id")
    private String userId;

    @Field(value = "organization_id")
    private String organizationId;

    @Field(value = "action")
    @Enumerated(EnumType.STRING)
    private FavoriteType action;

    @Field(value = "date_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dateTime;
}
