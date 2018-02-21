package com.omniesoft.commerce.notification.controllers;

import com.google.common.collect.Lists;
import com.omniesoft.commerce.common.ws.notification.payload.NotificationMessage;

import java.util.List;
import java.util.UUID;

public class StubMessage {

    private StubMessage() {
    }

    public static List<NotificationMessage> getNotificationMessages() {
        return Lists.newArrayList(new NotificationMessage() {
            @Override
            public String getStompUserName() {
                return "undefined";
            }

            @Override
            public UUID getOrganizationId() {
                return UUID.randomUUID();
            }
        });
    }
}
