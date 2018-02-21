package com.omniesoft.commerce.persistence.entity.cards.handbook;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 25.08.17
 */
@Entity
@Table(name = "handbook_phone")
public class HandbookPhoneEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "handbook_id")
    @JsonBackReference
    private HandbookEntity handbook;

    @Column(name = "phone")
    private String phone;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public HandbookEntity getHandbook() {
        return handbook;
    }

    public void setHandbook(HandbookEntity handbook) {
        this.handbook = handbook;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
