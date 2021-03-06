package com.omniesoft.commerce.common.notification.scope;

import com.omniesoft.commerce.persistence.entity.enums.NotifTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@AllArgsConstructor
@Getter
public class EventScope {
    private Principal eventEmitter;
    private String destination;
    private NotifTarget target;

}
