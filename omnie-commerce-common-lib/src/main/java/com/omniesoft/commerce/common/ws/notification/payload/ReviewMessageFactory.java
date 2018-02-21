package com.omniesoft.commerce.common.ws.notification.payload;

import com.omniesoft.commerce.persistence.entity.service.ServiceReviewEntity;

public class ReviewMessageFactory {

    private static Review create(
            com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity reviewEntity) {

        OrganizationReview organizationReview = new OrganizationReview();
        organizationReview.setOrganization(EnhancedMessageFactory.extract(reviewEntity.getOrganization()));
        organizationReview.setMark(reviewEntity.getMark());
        organizationReview.setText(reviewEntity.getText());
        organizationReview.setAcceptStatus(reviewEntity.getAcceptStatus());
        organizationReview.setId(reviewEntity.getId());
        organizationReview.setUser(EnhancedMessageFactory.extract(reviewEntity.getUser()));

        return organizationReview;
    }

    private static Review create(ServiceReviewEntity reviewEntity) {

        ServiceReview serviceReview = new ServiceReview();
        serviceReview.setService(EnhancedMessageFactory.extract(reviewEntity.getService()));
        serviceReview.setMark(reviewEntity.getMark());
        serviceReview.setText(reviewEntity.getText());
        serviceReview.setAcceptStatus(reviewEntity.getAcceptStatus());
        serviceReview.setId(reviewEntity.getId());
        serviceReview.setUser(EnhancedMessageFactory.extract(reviewEntity.getUser()));

        return serviceReview;
    }

    public static ReviewMessage compact(ServiceReviewEntity serviceReviewEntity) {
        return new ReviewMessage(create(serviceReviewEntity));
    }

    public static ReviewMessage compact(String recipientUserName, ServiceReviewEntity serviceReviewEntity) {
        return new ReviewMessage(create(serviceReviewEntity), recipientUserName);
    }

    public static ReviewMessage compact(
            com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity organizationReviewEntity) {
        return new ReviewMessage(create(organizationReviewEntity));
    }

    public static ReviewMessage compact(String recipientUserName, com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity organizationReviewEntity) {
        return new ReviewMessage(create(organizationReviewEntity), recipientUserName);
    }


}
