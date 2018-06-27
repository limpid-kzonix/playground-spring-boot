package com.omniesoft.commerce.notification.event.scope;

import com.omniesoft.commerce.notification.util.NotifTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.security.Principal;

/**
 * Generates by admin
 * Target user
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminEventScope extends EventScope {
    private String userReceiver;

    private AdminEventScope(Principal emitter, String destanation, String userReceiver) {
        super(emitter, destanation, NotifTarget.USER);
        this.userReceiver = userReceiver;
    }

    public static AdminEventScope of(Principal emitter, String destanation, String userReceiver) {
        return new AdminEventScope(emitter, destanation, userReceiver);
    }

}
