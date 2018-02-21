package com.omniesoft.commerce.persistence.entity.organization;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "favorite_organization")
public class OrganizationFavoriteEntity {
    @Id
    @EmbeddedId
    private OrganizationFavoriteKey id;

    public OrganizationFavoriteKey getId() {
        return id;
    }

    public void setId(OrganizationFavoriteKey id) {
        this.id = id;
    }
}
