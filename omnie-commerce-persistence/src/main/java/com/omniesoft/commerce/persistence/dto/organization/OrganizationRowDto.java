package com.omniesoft.commerce.persistence.dto.organization;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 11.08.17
 */
public class OrganizationRowDto {
    private UUID id;
    private String name;
    private String email;
    private String logoId;
    private Boolean deleteStatus;
    private Boolean freezeStatus;
    private LocalDateTime createTime;
    private UUID ownerId;
    private String ownerName;

    public OrganizationRowDto(UUID id, String name, String email, String logoId, Boolean deleteStatus, Boolean freezeStatus, LocalDateTime createDate, UUID ownerId, String ownerName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.logoId = logoId;
        this.deleteStatus = deleteStatus;
        this.freezeStatus = freezeStatus;
        this.createTime = createDate;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
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

    public Boolean getDeleted() {
        return deleteStatus;
    }

    public void setDeleted(Boolean deleted) {
        this.deleteStatus = deleted;
    }

    public Boolean getFreeze() {
        return freezeStatus;
    }

    public void setFreeze(Boolean freezeStatus) {
        this.freezeStatus = freezeStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
