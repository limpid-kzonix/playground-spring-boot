package com.omniesoft.commerce.persistence.entity.account;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 08.06.17
 */

@Entity
@Table(name = "user_setting")
@DynamicInsert
public class UserSettingEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "event_notification", nullable = false)
    private Boolean eventNotification;

    @Column(name = "push_notification", nullable = false)
    private Boolean pushNotification;

    @Column(name = "sound_notification", nullable = false)
    private Boolean soundNotification;

    @Column(name = "background_notification", nullable = false)
    private Boolean backgroundNotification;

    @Min(0)
    @Column(name = "notification_delay_minutes", nullable = false)
    private Integer notificationDelay;

    @Column(name = "calendar_sync", nullable = false)
    private Boolean calendarSync;

    @Column(name = "light_theme", nullable = false)
    private Boolean lightTheme;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getEventNotification() {
        return eventNotification;
    }

    public void setEventNotification(Boolean eventNotification) {
        this.eventNotification = eventNotification;
    }

    public Boolean getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(Boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public Boolean getSoundNotification() {
        return soundNotification;
    }

    public void setSoundNotification(Boolean soundNotification) {
        this.soundNotification = soundNotification;
    }

    public Boolean getBackgroundNotification() {
        return backgroundNotification;
    }

    public void setBackgroundNotification(Boolean backgroundNotification) {
        this.backgroundNotification = backgroundNotification;
    }

    public Integer getNotificationDelay() {
        return notificationDelay;
    }

    public void setNotificationDelay(Integer notificationDelay) {
        this.notificationDelay = notificationDelay;
    }

    public Boolean getCalendarSync() {
        return calendarSync;
    }

    public void setCalendarSync(Boolean calendarSync) {
        this.calendarSync = calendarSync;
    }

    public Boolean getLightTheme() {
        return lightTheme;
    }

    public void setLightTheme(Boolean lightTheme) {
        this.lightTheme = lightTheme;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
