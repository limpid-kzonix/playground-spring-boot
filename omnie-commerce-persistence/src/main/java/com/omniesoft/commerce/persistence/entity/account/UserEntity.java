package com.omniesoft.commerce.persistence.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import com.omniesoft.commerce.persistence.entity.cards.BusinessCardEntity;
import com.omniesoft.commerce.persistence.entity.cards.DiscountCardEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookEntity;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.entity.organization.BlackListEntity;
import com.omniesoft.commerce.persistence.entity.organization.CustomerGroupEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationFavoriteEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceFavoriteEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceReviewEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REFRESH;

/**
 * @author Vitalii Martynovskyi
 * @since 05.06.17
 */

@Entity
@Table(name = "users")
@JsonIgnoreProperties(value = {"encryptPassword", "salt", "passwordResets", "emailVerifications"})
public class UserEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", unique = true, nullable = false)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    @Pattern(regexp = "^(?=.*[a-z0-9]$)([a-z0-9][_.]?){3,50}$")
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Pattern(regexp = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "encrypt_password", nullable = false)
    private String encryptPassword;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY, cascade = REFRESH)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "uuid")})
    private List<AuthorityEntity> authorities;


    @ManyToMany(fetch = FetchType.LAZY, cascade = REFRESH)
    @JoinTable(
            name = "customers_group_to_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "uuid")}
    )
    private Set<CustomerGroupEntity> customerGroupEntities;

    @Column(name = "last_password_reset_date")
    private LocalDateTime lastPasswordResetDate;

    @OneToOne(cascade = ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id")
    private UserProfileEntity profile;

    @OneToOne(mappedBy = "user", cascade = ALL, fetch = FetchType.LAZY)
    private OwnerEntity ownerAccount;

    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_setting_id")
    private UserSettingEntity setting;

    @Column(name = "registration_date", nullable = false, insertable = false, updatable = false)
    private LocalDateTime registrationDate;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserEmailVerificationEntity> emailVerifications;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserEmailChangingVerificationEntity> emailChangingVerifications;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private List<UserPasswordResetEntity> passwordResets;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserOAuthEntity> oAuth;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<BusinessCardEntity> businessCards;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<DiscountCardEntity> discountCards;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "favorite_organization",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "organization_id", referencedColumnName = "uuid")}
    )
    private List<OrganizationEntity> favoriteOrganizations;

    @ManyToMany(cascade = ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_service",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")}
    )
    private List<ServiceEntity> favoriteServices;

    @OneToMany(mappedBy = "id.user")
    private List<ServiceFavoriteEntity> serviceFavoriteEntities;

    @OneToMany(mappedBy = "id.user")
    private List<OrganizationFavoriteEntity> organizationFavoriteEntities;


    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<ServiceReviewEntity> servicesReview;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity> organizationsReview;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderEntity> orders;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<BlackListEntity> atBlackList;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "template_order",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "uuid")}
    )
    private List<OrderEntity> templates;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "admin",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "uuid")}
    )
    private Set<AdminRoleEntity> roles;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "admin_to_service",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")}
    )
    private Set<ServiceEntity> adminAtServices;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_handbook",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "handbook_id", referencedColumnName = "uuid")}
    )
    @JsonManagedReference
    private List<HandbookEntity> handbook;


    public List<HandbookEntity> getHandbook() {

        return handbook;
    }

    public void setHandbook(List<HandbookEntity> handbook) {

        this.handbook = handbook;
    }

    public UUID getId() {

        return id;
    }

    public void setId(UUID id) {

        this.id = id;
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEncryptPassword() {

        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {

        this.encryptPassword = encryptPassword;
    }

    public UserProfileEntity getProfile() {

        return profile;
    }

    public void setProfile(UserProfileEntity profile) {

        this.profile = profile;
    }

    public UserSettingEntity getSetting() {

        return setting;
    }

    public void setSetting(UserSettingEntity setting) {

        this.setting = setting;
    }

    public LocalDateTime getRegistrationDate() {

        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {

        this.registrationDate = registrationDate;
    }

    public List<UserEmailVerificationEntity> getEmailVerifications() {

        return emailVerifications;
    }

    public void setEmailVerifications(List<UserEmailVerificationEntity> emailVerifications) {

        this.emailVerifications = emailVerifications;
    }

    public List<UserPasswordResetEntity> getPasswordResets() {

        return passwordResets;
    }

    public void setPasswordResets(List<UserPasswordResetEntity> passwordResets) {

        this.passwordResets = passwordResets;
    }

    public List<UserOAuthEntity> getoAuth() {

        return oAuth;
    }

    public void setoAuth(List<UserOAuthEntity> oAuth) {

        this.oAuth = oAuth;
    }

    public List<BusinessCardEntity> getBusinessCards() {

        return businessCards;
    }

    public void setBusinessCards(List<BusinessCardEntity> businessCards) {

        this.businessCards = businessCards;
    }

    public List<DiscountCardEntity> getDiscountCards() {

        return discountCards;
    }

    public void setDiscountCards(List<DiscountCardEntity> discountCards) {

        this.discountCards = discountCards;
    }

    public List<OrganizationEntity> getFavoriteOrganizations() {

        return favoriteOrganizations;
    }

    public void setFavoriteOrganizations(List<OrganizationEntity> favoriteOrganizations) {

        this.favoriteOrganizations = favoriteOrganizations;
    }

    public List<ServiceEntity> getFavoriteServices() {

        return favoriteServices;
    }

    public void setFavoriteServices(List<ServiceEntity> favoriteServices) {

        this.favoriteServices = favoriteServices;
    }

    public List<AuthorityEntity> getAuthorities() {

        return authorities;
    }

    public void setAuthorities(List<AuthorityEntity> authorities) {

        this.authorities = authorities;
    }

    public Boolean getEnabled() {

        return enabled;
    }

    public void setEnabled(Boolean enabled) {

        this.enabled = enabled;
    }

    public LocalDateTime getLastPasswordResetDate() {

        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(LocalDateTime lastPasswordResetDate) {

        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public OwnerEntity getOwnerAccount() {

        return ownerAccount;
    }

    public void setOwnerAccount(OwnerEntity ownerAccount) {

        this.ownerAccount = ownerAccount;
    }

    public List<ServiceReviewEntity> getServicesReview() {

        return servicesReview;
    }

    public void setServicesReview(List<ServiceReviewEntity> servicesReview) {

        this.servicesReview = servicesReview;
    }

    public List<com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity> getOrganizationsReview() {

        return organizationsReview;
    }

    public void setOrganizationsReview(
            List<com.omniesoft.commerce.persistence.entity.organization.OrganizationReviewEntity> organizationsReview) {

        this.organizationsReview = organizationsReview;
    }

    public List<OrderEntity> getOrders() {

        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {

        this.orders = orders;
    }

    public List<BlackListEntity> getAtBlackList() {

        return atBlackList;
    }

    public void setAtBlackList(List<BlackListEntity> atBlackList) {

        this.atBlackList = atBlackList;
    }

    public List<OrderEntity> getTemplates() {

        return templates;
    }

    public void setTemplates(List<OrderEntity> templates) {

        this.templates = templates;
    }

    public Set<AdminRoleEntity> getRoles() {

        return roles;
    }

    public void setRoles(Set<AdminRoleEntity> roles) {

        this.roles = roles;
    }

    public Set<ServiceEntity> getAdminAtServices() {

        return adminAtServices;
    }

    public void setAdminAtServices(Set<ServiceEntity> adminAtServices) {

        this.adminAtServices = adminAtServices;
    }

    public Set<CustomerGroupEntity> getCustomerGroupEntities() {

        return customerGroupEntities;
    }

    public void setCustomerGroupEntities(
            Set<CustomerGroupEntity> customerGroupEntities) {

        this.customerGroupEntities = customerGroupEntities;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(registrationDate, that.registrationDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, login, registrationDate);
    }

    public List<ServiceFavoriteEntity> getServiceFavoriteEntities() {

        return serviceFavoriteEntities;
    }

    public void setServiceFavoriteEntities(List<ServiceFavoriteEntity> serviceFavoriteEntities) {

        this.serviceFavoriteEntities = serviceFavoriteEntities;
    }

    public List<OrganizationFavoriteEntity> getOrganizationFavoriteEntities() {

        return organizationFavoriteEntities;
    }

    public void setOrganizationFavoriteEntities(
            List<OrganizationFavoriteEntity> organizationFavoriteEntities) {

        this.organizationFavoriteEntities = organizationFavoriteEntities;
    }

    public List<UserEmailChangingVerificationEntity> getEmailChangingVerifications() {
        return emailChangingVerifications;
    }

    public void setEmailChangingVerifications(List<UserEmailChangingVerificationEntity> emailChangingVerifications) {
        this.emailChangingVerifications = emailChangingVerifications;
    }
}