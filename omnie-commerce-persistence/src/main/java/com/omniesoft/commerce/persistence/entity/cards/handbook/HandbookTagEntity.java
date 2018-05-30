package com.omniesoft.commerce.persistence.entity.cards.handbook;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 25.08.17
 */
@Entity
@Table(name = "handbook_tag")
public class HandbookTagEntity {

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

    @Column(name = "tag")
    private String tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandbookTagEntity that = (HandbookTagEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, tag);
    }
}
