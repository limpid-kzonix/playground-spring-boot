package com.omniesoft.commerce.persistence.entity.admin;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.06.17
 */
@Entity
@Table(name = "admin_role")
public class AdminRoleEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "admin",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")}
    )
    private Set<UserEntity> admins;

    @OneToMany(mappedBy = "role", cascade = CascadeType.REFRESH)
    private Set<AdminRolePermissionEntity> permissions;

    public AdminRoleEntity() {
    }

    public AdminRoleEntity(String name, OrganizationEntity organization) {
        this.name = name;
        this.organization = organization;
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


    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public Set<AdminRolePermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<AdminRolePermissionEntity> permisions) {
        this.permissions = permisions;
    }

    public Set<UserEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<UserEntity> admins) {
        this.admins = admins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminRoleEntity that = (AdminRoleEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
