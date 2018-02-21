package com.omniesoft.commerce.persistence.entity.admin;

import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.06.17
 */
@Entity
@Table(name = "admin_role_permission")
public class AdminRolePermissionEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "admin_role_id")
    private AdminRoleEntity role;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false)
    private AdminPermission permission;

    public AdminRolePermissionEntity() {
    }

    public AdminRolePermissionEntity(AdminRoleEntity role, AdminPermission permission) {
        this.role = role;
        this.permission = permission;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AdminRoleEntity getRole() {
        return role;
    }

    public void setRole(AdminRoleEntity role) {
        this.role = role;
    }

    public AdminPermission getPermission() {
        return permission;
    }

    public void setPermission(AdminPermission permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminRolePermissionEntity that = (AdminRolePermissionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
