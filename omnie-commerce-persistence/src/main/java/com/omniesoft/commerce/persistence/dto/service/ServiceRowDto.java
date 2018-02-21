package com.omniesoft.commerce.persistence.dto.service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 17.08.17
 */
public class ServiceRowDto {
    private UUID id;
    private String name;
    private String logo;
    private Boolean deleted;
    private Boolean freeze;
    private LocalDateTime createDate;
    private UUID organizationId;
    private String organizationName;

    public ServiceRowDto(UUID id, String name, String logo, Boolean deleted, Boolean freeze, LocalDateTime createDate, UUID organizationId, String organizationName) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.deleted = deleted;
        this.freeze = freeze;
        this.createDate = createDate;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getFreeze() {
        return freeze;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
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
}
