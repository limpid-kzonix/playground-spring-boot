package com.omniesoft.commerce.persistence.dto.organization;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrganizationRowExtendDto {
    private UUID id;
    private String name;
    private String email;
    private String logoId;
    private Boolean freezeStatus;
    private LocalDateTime createTime;
    private Boolean isFavorite;
    private Double rating;
    private String title;
    private String description;
    private String inaccessibilityReason;
    private String placeId;
    private Double longitude;
    private Double latitude;

    public OrganizationRowExtendDto(UUID id, String name, String email, String logoId, Boolean freezeStatus, LocalDateTime createTime, Boolean isFavorite, Double rating, String title, String description, String inaccessibilityReason, String placeId, Double longitude, Double latitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.logoId = logoId;
        this.freezeStatus = freezeStatus;
        this.createTime = createTime;
        this.isFavorite = isFavorite;
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.inaccessibilityReason = inaccessibilityReason;
        this.placeId = placeId;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public Boolean getFreezeStatus() {
        return freezeStatus;
    }

    public void setFreezeStatus(Boolean freezeStatus) {
        this.freezeStatus = freezeStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInaccessibilityReason() {
        return inaccessibilityReason;
    }

    public void setInaccessibilityReason(String inaccessibilityReason) {
        this.inaccessibilityReason = inaccessibilityReason;
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
