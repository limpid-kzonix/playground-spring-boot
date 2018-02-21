package com.omniesoft.commerce.common.ws.notification.payload;

import java.util.UUID;


/**
 * Abstraction of message for notification (STOP-over-SOCKET) service
 */

public abstract class NotificationMessage {
    /**
     * userName -  May blank or null if it isn`t direct message (if you don`t know username of recipient)
     */
    public abstract String getStompUserName();

    public abstract UUID getOrganizationId();
}
