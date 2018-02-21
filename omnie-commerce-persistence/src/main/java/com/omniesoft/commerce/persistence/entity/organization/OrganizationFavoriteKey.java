package com.omniesoft.commerce.persistence.entity.organization;

import com.google.common.base.Objects;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Embeddable composite key
 */
@Embeddable
public class OrganizationFavoriteKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "uuid")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "organization_id", referencedColumnName = "uuid")
    private OrganizationEntity organization;

    public OrganizationFavoriteKey(UserEntity user, OrganizationEntity organization) {
        this.user = user;
        this.organization = organization;
    }

    public OrganizationFavoriteKey() {
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationFavoriteKey)) return false;
        OrganizationFavoriteKey that = (OrganizationFavoriteKey) o;
        return Objects.equal(getUser(), that.getUser()) &&
                Objects.equal(getOrganization(), that.getOrganization());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUser(), getOrganization());
    }
}
