package com.omniesoft.commerce.persistence.entity.notification;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.NotifTarget;
import com.omniesoft.commerce.persistence.entity.enums.NotifType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
public class NotifEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    @Column(name = "item_id")
    private UUID item;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NotifType type;

    @Column(name = "target")
    @Enumerated(EnumType.STRING)
    private NotifTarget target;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public UUID getItem() {
        return item;
    }

    public void setItem(UUID item) {
        this.item = item;
    }

    public NotifType getType() {
        return type;
    }

    public void setType(NotifType type) {
        this.type = type;
    }

    public NotifTarget getTarget() {
        return target;
    }

    public void setTarget(NotifTarget target) {
        this.target = target;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
