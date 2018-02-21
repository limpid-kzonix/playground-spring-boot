package com.omniesoft.commerce.persistence.entity.service;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
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
@Table(name = "service_timing")
public class ServiceTimingEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Column(name = "min_duration")
    private Boolean minDuration;

    @Column(name = "duration_millis")
    private Long durationMillis;

    @Column(name = "pause_millis")
    private Long pauseMillis;

    @Column(name = "slot_count")
    private Integer slotCount;

    @Column(name = "max_discount")
    private Double maxDiscount;

    @Column(name = "active_from", nullable = false)
    private LocalDateTime activeFrom;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "update_by")
    private UserEntity updateByUser;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public Boolean getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Boolean minDuration) {
        this.minDuration = minDuration;
    }

    public Long getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(Long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public Long getPauseMillis() {
        return pauseMillis;
    }

    public void setPauseMillis(Long pauseMillis) {
        this.pauseMillis = pauseMillis;
    }

    public Integer getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(Integer slotCount) {
        this.slotCount = slotCount;
    }

    public LocalDateTime getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDateTime activeFrom) {
        this.activeFrom = activeFrom;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public UserEntity getUpdateByUser() {
        return updateByUser;
    }

    public void setUpdateByUser(UserEntity updateByUser) {
        this.updateByUser = updateByUser;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

}
