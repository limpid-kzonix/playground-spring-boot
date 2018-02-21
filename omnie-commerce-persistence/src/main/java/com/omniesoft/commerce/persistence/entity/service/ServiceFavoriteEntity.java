package com.omniesoft.commerce.persistence.entity.service;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "favorite_service")
public class ServiceFavoriteEntity {
    @Id
    @EmbeddedId
    private ServiceFavoriteKey id;

    public ServiceFavoriteKey getId() {
        return id;
    }

    public void setId(ServiceFavoriteKey id) {
        this.id = id;
    }
}
