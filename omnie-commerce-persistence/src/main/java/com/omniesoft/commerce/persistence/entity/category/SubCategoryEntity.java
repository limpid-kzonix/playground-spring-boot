package com.omniesoft.commerce.persistence.entity.category;

import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 14.07.17
 * <p>
 * CREATE TABLE IF NOT EXISTS subCategory (
 * uuid       UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
 * categoryId UUID         NOT NULL REFERENCES category (uuid) ON DELETE CASCADE,
 * engName    VARCHAR(200) NOT NULL,
 * ruName     VARCHAR(200) NOT NULL,
 * uaName     VARCHAR(200) NOT NULL,
 * createDate TIMESTAMP    NOT NULL DEFAULT now()
 * );
 */
@Entity
@Table(name = "sub_category")
public class SubCategoryEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "rus_name")
    private String rusName;

    @Column(name = "ukr_name")
    private String ukrName;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "service_category",
            joinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "uuid")}
    )
    private ServiceEntity service;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getRusName() {
        return rusName;
    }

    public void setRusName(String rusName) {
        this.rusName = rusName;
    }

    public String getUkrName() {
        return ukrName;
    }

    public void setUkrName(String ukrName) {
        this.ukrName = ukrName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }
}
