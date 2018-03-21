package com.omniesoft.commerce.persistence.entity.category;

import com.omniesoft.commerce.persistence.entity.enums.LanguageCode;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceFavoriteEntity;
import com.omniesoft.commerce.persistence.entity.service.ServiceLanguageEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
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

    @OneToMany(mappedBy = "id.language")
    private List<ServiceLanguageEntity> serviceLanguages;

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

    public List<ServiceLanguageEntity> getServiceLanguages() {
        return serviceLanguages;
    }

    public void setServiceLanguages(List<ServiceLanguageEntity> serviceLanguages) {
        this.serviceLanguages = serviceLanguages;
    }
}
