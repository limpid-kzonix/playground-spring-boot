package com.omniesoft.commerce.persistence.entity.organization;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.07.17
 */
@Entity
@Table(name = "organization_phone")
public class OrganizationPhoneEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @Pattern(regexp = "(^(\\+\\d{1,5})?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}$)|(\\+[\\d\\s]{7,20})$")
    @Column(name = "phone")
    private String phone;

    public OrganizationPhoneEntity() {
    }

    public OrganizationPhoneEntity(OrganizationEntity organization, String phone) {
        this.organization = organization;
        this.phone = phone;
    }

    public OrganizationPhoneEntity(UUID id, OrganizationEntity organization, String phone) {
        this.id = id;
        this.organization = organization;
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}