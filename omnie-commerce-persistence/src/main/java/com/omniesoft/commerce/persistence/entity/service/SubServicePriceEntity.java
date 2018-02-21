package com.omniesoft.commerce.persistence.entity.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.MeasurementUnit;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 17.09.17
 */
@Entity
@Table(name = "sub_service_price")
public class SubServicePriceEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sub_service_id")
    private SubServiceEntity subService;

    @Column(name = "active_from", nullable = false)
    private LocalDateTime activeFrom;

    @Column(name = "measurement_unit")
    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;

    @Column(name = "duration_millis")
    private Long durationMillis;

    @Column(name = "expense")
    private Double expense;

    @Column(name = "price")
    private Double price;

    @Column(name = "min_count")
    private Integer minCount;

    @Column(name = "max_count")
    private Integer maxCount;

    @Column(name = "max_discount")
    private Double maxDiscount;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "update_by")
    private UserEntity updateByUser;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SubServiceEntity getSubService() {
        return subService;
    }

    public void setSubService(SubServiceEntity subService) {
        this.subService = subService;
    }

    public LocalDateTime getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDateTime activeFrom) {
        this.activeFrom = activeFrom;
    }


    public Long getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(Long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public UserEntity getUpdateByUser() {
        return updateByUser;
    }

    public void setUpdateByUser(UserEntity updateByUser) {
        this.updateByUser = updateByUser;
    }

    public MeasurementUnit getMeasurementUnit() {

        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {

        this.measurementUnit = measurementUnit;
    }
}
