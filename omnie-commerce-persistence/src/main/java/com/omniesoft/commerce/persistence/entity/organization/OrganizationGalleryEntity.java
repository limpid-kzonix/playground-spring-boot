package com.omniesoft.commerce.persistence.entity.organization;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.07.17
 */
@Entity
@Table(name = "organization_gallery")
public class OrganizationGalleryEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @Column(name = "image_id")
    private String imageId;

    public OrganizationGalleryEntity() {
    }

    public OrganizationGalleryEntity(OrganizationEntity organization, String imageId) {
        this.organization = organization;
        this.imageId = imageId;
    }

    public OrganizationGalleryEntity(UUID id, OrganizationEntity organization) {
        this.id = id;
        this.organization = organization;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
