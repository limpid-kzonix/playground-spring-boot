package com.omniesoft.commerce.persistence.entity.service;

import com.google.common.base.Objects;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.category.LanguageEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Embeddable composite key
 */
@Embeddable
public class ServiceLanguageKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "language_id", referencedColumnName = "uuid")
    private LanguageEntity language;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "service_id", referencedColumnName = "uuid")
    private ServiceEntity service;

    public ServiceLanguageKey() {
    }

    public ServiceLanguageKey(LanguageEntity language, ServiceEntity service) {
        this.language = language;
        this.service = service;
    }

    public LanguageEntity getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEntity language) {
        this.language = language;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceLanguageKey)) return false;
        ServiceLanguageKey that = (ServiceLanguageKey) o;
        return Objects.equal(this.getLanguage(), that.getLanguage()) &&
                Objects.equal(this.getService(), that.getService());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getLanguage(), this.getService());
    }
}
