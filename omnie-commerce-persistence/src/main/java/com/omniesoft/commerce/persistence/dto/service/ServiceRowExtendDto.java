package com.omniesoft.commerce.persistence.dto.service;

import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceRowExtendDto {
    private UUID id;
    private String name;
    private String description;
    private String logo;
    private Boolean freeze;
    private String inaccessibilityReason;
    private LocalDateTime createTime;
    private UUID organizationId;
    private String organizationName;
    private Boolean isFavorite;
    private Double rating;

    public ServiceRowExtendDto(UUID id, String name, String description, String logo, Boolean freeze, String inaccessibilityReason, LocalDateTime createTime, UUID organizationId, String organizationName, Boolean isFavorite, Double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.freeze = freeze;
        this.inaccessibilityReason = inaccessibilityReason;
        this.createTime = createTime;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.isFavorite = isFavorite;
        this.rating = rating;
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


}
