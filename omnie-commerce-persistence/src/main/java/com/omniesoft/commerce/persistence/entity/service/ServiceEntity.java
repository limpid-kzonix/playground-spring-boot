package com.omniesoft.commerce.persistence.entity.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.category.LanguageEntity;
import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.07.17
 */
@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "logo_id")
    private String logoId;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by", updatable = false)
    private UserEntity createByUser;

    @OneToMany(mappedBy = "service")
    private List<ServiceGalleryEntity> gallery;


    @Column(name = "freeze_status")
    private Boolean freezeStatus;

    @Column(name = "delete_status")
    private Boolean deleteStatus;

    @Column(name = "reason")
    private String reason;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<SubServiceEntity> subServices;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private Set<ServicePriceEntity> prices;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<ServiceTimingEntity> timing;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<ServiceReviewEntity> reviews;

    @OneToOne(mappedBy = "service", fetch = FetchType.LAZY)
    private ServiceMarkEntity mark;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "service_category",
            joinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "uuid")}
    )
    private Set<SubCategoryEntity> subCategories;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_service",
            joinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")}
    )
    private List<UserEntity> inUsersFavorites;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "discount_for_service",
            joinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "discount_id", referencedColumnName = "uuid")}
    )
    private List<DiscountEntity> associatedDiscounts;


    @OneToMany(mappedBy = "id.service", cascade = CascadeType.REFRESH)
    private List<ServiceFavoriteEntity> favorites;

    @OneToMany(mappedBy = "id.service", cascade = CascadeType.REFRESH)
    private List<ServiceLanguageEntity> languages;


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "admin_to_service",
            joinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")}
    )
    private List<UserEntity> admins;

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

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public UserEntity getCreateByUser() {
        return createByUser;
    }

    public void setCreateByUser(UserEntity createByUser) {
        this.createByUser = createByUser;
    }

    public List<ServiceGalleryEntity> getGallery() {
        return gallery;
    }

    public void setGallery(List<ServiceGalleryEntity> gallery) {
        this.gallery = gallery;
    }

    public List<SubServiceEntity> getSubServices() {
        return subServices;
    }

    public void setSubServices(List<SubServiceEntity> subServices) {
        this.subServices = subServices;
    }

    public Set<ServicePriceEntity> getPrices() {
        return prices;
    }

    public void setPrices(Set<ServicePriceEntity> prices) {
        this.prices = prices;
    }

    public List<ServiceReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ServiceReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public ServiceMarkEntity getMark() {
        return mark;
    }

    public void setMark(ServiceMarkEntity mark) {
        this.mark = mark;
    }

    public Set<SubCategoryEntity> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<SubCategoryEntity> subCategories) {
        this.subCategories = subCategories;
    }

    public List<UserEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(List<UserEntity> admins) {
        this.admins = admins;
    }

    public List<ServiceTimingEntity> getTiming() {
        return timing;
    }

    public void setTiming(List<ServiceTimingEntity> timing) {
        this.timing = timing;
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

    public List<ServiceFavoriteEntity> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<ServiceFavoriteEntity> favorites) {
        this.favorites = favorites;
    }

    public List<DiscountEntity> getAssociatedDiscounts() {
        return associatedDiscounts;
    }

    public void setAssociatedDiscounts(List<DiscountEntity> associatedDiscounts) {
        this.associatedDiscounts = associatedDiscounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceEntity that = (ServiceEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime);
    }

    public List<ServiceLanguageEntity> getLanguages() {
        return languages;
    }

    public void setLanguages(List<ServiceLanguageEntity> languages) {
        this.languages = languages;
    }
}
