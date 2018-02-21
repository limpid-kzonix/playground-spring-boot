package com.omniesoft.commerce.persistence.entity.organization;

import com.omniesoft.commerce.persistence.entity.account.OwnerEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

/**
 * @author Vitalii Martynovskyi
 * @since 12.06.17
 */

@Entity
@Table(name = "organization")
public class OrganizationEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "logo_id")
    private String logoId;

    @Column(name = "background_image_id")
    private String backgroundImageId;

    @Column(name = "placeid")
    private String placeId;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "freeze_status")
    private Boolean freezeStatus;

    @Column(name = "delete_status")
    private Boolean deleteStatus;

    @Column(name = "reason")
    private String reason;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    @OneToOne(mappedBy = "organization", fetch = FetchType.LAZY, cascade = ALL, optional = false)
    private OrganizationMarkEntity mark;

    @ManyToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<OrganizationGalleryEntity> gallery;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<OrganizationPhoneEntity> phones;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<OrganizationTimeSheetEntity> timesheet;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<ServiceEntity> services;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<AdminRoleEntity> roles;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<OrganizationReviewEntity> reviews;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<NewsEntity> news;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<CustomerGroupEntity> groups;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<BlackListEntity> blackList;

    @ManyToMany(cascade = ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_organization",
            joinColumns = {@JoinColumn(name = "organization_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")}
    )
    private List<UserEntity> inUsersFavorites;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "black_list",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "organization_id", referencedColumnName = "uuid")}
    )
    private List<UserEntity> usersFromBlackList;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public String getBackgroundImageId() {
        return backgroundImageId;
    }

    public void setBackgroundImageId(String backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public OwnerEntity getOwner() {
        return owner;
    }

    public void setOwner(OwnerEntity owner) {
        this.owner = owner;
    }

    public List<OrganizationGalleryEntity> getGallery() {
        return gallery;
    }

    public void setGallery(List<OrganizationGalleryEntity> gallery) {
        this.gallery = gallery;
    }

    public List<OrganizationPhoneEntity> getPhones() {
        return phones;
    }

    public void setPhones(List<OrganizationPhoneEntity> phones) {
        this.phones = phones;
    }

    public List<OrganizationTimeSheetEntity> getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(List<OrganizationTimeSheetEntity> timesheet) {
        this.timesheet = timesheet;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    public List<AdminRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<AdminRoleEntity> roles) {
        this.roles = roles;
    }

    public List<OrganizationReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<OrganizationReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public List<CustomerGroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<CustomerGroupEntity> groups) {
        this.groups = groups;
    }

    public List<BlackListEntity> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<BlackListEntity> blackList) {
        this.blackList = blackList;
    }

    public OrganizationMarkEntity getMark() {
        return mark;
    }

    public void setMark(OrganizationMarkEntity mark) {
        this.mark = mark;
    }

    public List<UserEntity> getUsersFromBlackList() {
        return usersFromBlackList;
    }

    public void setUsersFromBlackList(List<UserEntity> usersFromBlackList) {
        this.usersFromBlackList = usersFromBlackList;
    }

    public List<NewsEntity> getNews() {
        return news;
    }

    public void setNews(List<NewsEntity> news) {
        this.news = news;
    }

    public List<UserEntity> getInUsersFavorites() {
        return inUsersFavorites;
    }

    public void setInUsersFavorites(List<UserEntity> inUsersFavorites) {
        this.inUsersFavorites = inUsersFavorites;
    }

    public Boolean getFreezeStatus() {
        return freezeStatus;
    }

    public void setFreezeStatus(Boolean freezeStatus) {
        this.freezeStatus = freezeStatus;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "OrganizationEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", logoId='" + logoId + '\'' +
                ", backgroundImageId='" + backgroundImageId + '\'' +
                ", placeId='" + placeId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationEntity that = (OrganizationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime);
    }
}
