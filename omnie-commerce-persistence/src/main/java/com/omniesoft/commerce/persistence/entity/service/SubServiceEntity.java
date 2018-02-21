package com.omniesoft.commerce.persistence.entity.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.07.17
 */
@Entity
@Table(name = "sub_service")
@DynamicInsert
public class SubServiceEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "delete_status")
    private Boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;


    @OneToMany(mappedBy = "subService", cascade = CascadeType.ALL)
    private List<SubServicePriceEntity> prices;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by", updatable = false)
    private UserEntity createByUser;


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

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
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

    public List<SubServicePriceEntity> getPrices() {
        return prices;
    }

    public void setPrices(List<SubServicePriceEntity> prices) {
        this.prices = prices;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubServiceEntity that = (SubServiceEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime);
    }
}
