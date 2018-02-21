package com.omniesoft.commerce.persistence.projection.account;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AccountSettingSummary {
    UUID getId();

    Boolean getEventNotification();

    Boolean getPushNotification();

    Boolean getSoundNotification();

    Boolean getBackgroundNotification();

    Integer getNotificationDelay();

    Boolean getCalendarSync();

    Boolean getLightTheme();

    LocalDateTime getCreateTime();
}
