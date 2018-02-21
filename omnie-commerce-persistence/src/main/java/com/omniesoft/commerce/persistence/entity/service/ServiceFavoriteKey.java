package com.omniesoft.commerce.persistence.entity.service;

import com.google.common.base.Objects;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Embeddable composite key
 */
@Embeddable
public class ServiceFavoriteKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", referencedColumnName = "uuid")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "service_id", referencedColumnName = "uuid")
    private ServiceEntity service;

    public ServiceFavoriteKey(UserEntity user, ServiceEntity service) {
        this.user = user;
        this.service = service;
    }

    public ServiceFavoriteKey() {

    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
        if (!(o instanceof ServiceFavoriteKey)) return false;
        ServiceFavoriteKey that = (ServiceFavoriteKey) o;
        return Objects.equal(getUser(), that.getUser()) &&
                Objects.equal(service, that.service);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUser(), service);
    }
}
