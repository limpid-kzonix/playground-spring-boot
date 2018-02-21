package com.omniesoft.commerce.common.ws.notification.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewMessage extends NotificationMessage {

    private Review review;
    private String userName;


    public ReviewMessage(Review review) {

        this.review = review;
    }

    @Override
    public String getStompUserName() {

        return getUserName();
    }

    public UUID getOrganizationId() {
        return getReview().getOrganizationId();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
abstract class Review {
    private UUID id;

    private User user;

    private Integer mark;

    private String text;

    private Boolean acceptStatus;

    abstract UUID getOrganizationId();
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
class OrganizationReview extends Review {

    private Organization organization;

    @Override
    UUID getOrganizationId() {

        return organization.getId();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
class ServiceReview extends Review {

    private Service service;

    @Override
    UUID getOrganizationId() {

        return service.getOrganization().getId();
    }
}

