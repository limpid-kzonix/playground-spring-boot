package com.omniesoft.commerce.persistence.entity.cards.handbook;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 25.08.17
 */
@Entity
@Table(name = "handbook")
public class HandbookEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "accept_status")
    private Boolean acceptStatus;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "user_handbook",
            joinColumns =
            @JoinColumn(name = "handbook_id", referencedColumnName = "uuid"),
            inverseJoinColumns =
            @JoinColumn(name = "user_id", referencedColumnName = "uuid"))
    private UserEntity userEntity;

    @OneToMany(mappedBy = "handbook", cascade = CascadeType.ALL)
    private Set<HandbookPhoneEntity> phones;

    @OneToMany(mappedBy = "handbook", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<HandbookTagEntity> tags;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Boolean getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(Boolean acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public Set<HandbookPhoneEntity> getPhones() {
        return phones;
    }

    public void setPhones(Set<HandbookPhoneEntity> phones) {
        this.phones = phones;
    }

    public Set<HandbookTagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<HandbookTagEntity> tags) {
        this.tags = tags;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
