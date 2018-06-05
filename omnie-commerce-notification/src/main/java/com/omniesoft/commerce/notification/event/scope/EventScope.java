package com.omniesoft.commerce.notification.event.scope;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@AllArgsConstructor
@Getter
public class EventScope {
    private Principal eventEmitter;
    private String destination;

}
