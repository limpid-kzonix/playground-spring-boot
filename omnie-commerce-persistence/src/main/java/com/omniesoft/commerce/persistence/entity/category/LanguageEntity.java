package com.omniesoft.commerce.persistence.entity.category;

import com.omniesoft.commerce.persistence.entity.enums.LanguageCode;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 17.09.17
 */
@Entity
@Table(name = "language")
public class LanguageEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private LanguageCode name;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "service_language",
            joinColumns = {@JoinColumn(name = "language_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")}
    )
    private ServiceEntity service;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LanguageCode getName() {
        return name;
    }

    public void setName(LanguageCode name) {
        this.name = name;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }
}
