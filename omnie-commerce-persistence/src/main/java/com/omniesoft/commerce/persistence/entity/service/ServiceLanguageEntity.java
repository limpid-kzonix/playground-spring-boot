package com.omniesoft.commerce.persistence.entity.service;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_language")
public class ServiceLanguageEntity {
    @Id
    @EmbeddedId
    private ServiceLanguageKey id;

    public ServiceLanguageKey getId() {
        return id;
    }

    public void setId(ServiceLanguageKey id) {
        this.id = id;
    }
}
