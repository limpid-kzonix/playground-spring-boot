package com.omniesoft.commerce.persistence.entity.discount;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 23.08.17
 */
@Entity
@Table(name = "discount")
public class DiscountEntity {
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

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;


    @Column(name = "percent")
    private Double percent;

    @Column(name = "personal_status")
    private Boolean personalStatus;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "update_by")
    private UserEntity updateByUser;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "discount_for_user",
            joinColumns = {@JoinColumn(name = "discount_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")}
    )
    private Set<UserEntity> associatedUsers;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "discount_for_service",
            joinColumns = {@JoinColumn(name = "discount_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")}
    )
    private Set<ServiceEntity> associatedServices;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "discount_for_sub_service",
            joinColumns = {@JoinColumn(name = "discount_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "sub_service_id", referencedColumnName = "uuid")}
    )
    private Set<SubServiceEntity> associatedSubServices;

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Boolean getPersonalStatus() {
        return personalStatus;
    }

    public void setPersonalStatus(Boolean personalStatus) {
        this.personalStatus = personalStatus;
    }

    public Set<UserEntity> getAssociatedUsers() {
        return associatedUsers;
    }

    public void setAssociatedUsers(Set<UserEntity> associatedUsers) {
        this.associatedUsers = associatedUsers;
    }

    public Set<ServiceEntity> getAssociatedServices() {
        return associatedServices;
    }

    public void setAssociatedServices(Set<ServiceEntity> associatedServices) {
        this.associatedServices = associatedServices;
    }

    public Set<SubServiceEntity> getAssociatedSubServices() {
        return associatedSubServices;
    }

    public void setAssociatedSubServices(Set<SubServiceEntity> associatedSubServices) {
        this.associatedSubServices = associatedSubServices;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public UserEntity getUpdateByUser() {
        return updateByUser;
    }

    public void setUpdateByUser(UserEntity updateByUser) {
        this.updateByUser = updateByUser;
    }
}
