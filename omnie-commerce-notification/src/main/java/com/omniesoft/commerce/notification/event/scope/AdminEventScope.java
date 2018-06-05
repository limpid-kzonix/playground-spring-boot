package com.omniesoft.commerce.notification.event.scope;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.security.Principal;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminEventScope extends EventScope {
    private String userReceiver;

    private AdminEventScope(Principal emitter, String destanation, String userReceiver) {
        super(emitter, destanation);
        this.userReceiver = userReceiver;
    }

    public static AdminEventScope of(Principal emitter, String destanation, String userReceiver) {
        return new AdminEventScope(emitter, destanation, userReceiver);
    }

}
