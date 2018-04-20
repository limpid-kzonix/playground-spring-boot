/*
 * Copyright (c)  2017
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.persistence.dto.service;

import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceRowUserExtendDto {

    private UUID id;
    private String name;
    private String description;
    private String logo;
    private Boolean freeze;
    private String inaccessibilityReason;
    private LocalDateTime createTime;
    private Boolean isFavorite;
    private Double rating;

    private OrganizationInnerInfo organizationInnerInfo;

    public ServiceRowUserExtendDto(UUID id,
                                   String name,
                                   String description,
                                   String logo,
                                   Boolean freeze,
                                   String inaccessibilityReason,
                                   LocalDateTime createTime,
                                   Boolean isFavorite,
                                   Double rating,
                                   UUID organizationId,
                                   String organizationName,
                                   String organizationPlaceId,
                                   Double organizationLongitude,
                                   Double organizationLatitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.freeze = freeze;
        this.inaccessibilityReason = inaccessibilityReason;
        this.createTime = createTime;
        this.isFavorite = isFavorite;
        this.rating = rating;
        organizationInnerInfo = new OrganizationInnerInfo();
        this.organizationInnerInfo.setOrganizationId(organizationId);
        this.organizationInnerInfo.setOrganizationName(organizationName);
        this.organizationInnerInfo.setPlaceId(organizationPlaceId);
        this.organizationInnerInfo.setLongitude(organizationLongitude);
        this.organizationInnerInfo.setLatitude(organizationLatitude);

    }

    public UUID getId() {

        return id;
    }

    public void setId(UUID id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getLogo() {

        return logo;
    }

    public void setLogo(String logo) {

        this.logo = logo;
    }

    public Boolean getFreeze() {

        return freeze;
    }

    public void setFreeze(Boolean freeze) {

        this.freeze = freeze;
    }

    public String getInaccessibilityReason() {

        return inaccessibilityReason;
    }

    public void setInaccessibilityReason(String inaccessibilityReason) {

        this.inaccessibilityReason = inaccessibilityReason;
    }

    public LocalDateTime getCreateDate() {

        return createTime;
    }

    public void setCreateDate(LocalDateTime createDate) {

        this.createTime = createDate;
    }


    public Boolean getFavorite() {

        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {

        isFavorite = favorite;
    }

    public Double getRating() {

        return rating;
    }

    public void setRating(Double rating) {

        this.rating = rating;
    }

    public OrganizationInnerInfo getOrganizationInnerInfo() {
        return organizationInnerInfo;
    }

    public void setOrganizationInnerInfo(OrganizationInnerInfo organizationInnerInfo) {
        this.organizationInnerInfo = organizationInnerInfo;
    }

    private static class OrganizationInnerInfo {
        private UUID organizationId;
        private String organizationName;
        private String placeId;
        private Double longitude;
        private Double latitude;

        public OrganizationInnerInfo() {

        }

        public OrganizationInnerInfo(UUID organizationId, String organizationName) {
            this.organizationId = organizationId;
            this.organizationName = organizationName;
        }

        public UUID getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(UUID organizationId) {
            this.organizationId = organizationId;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
    }
}
