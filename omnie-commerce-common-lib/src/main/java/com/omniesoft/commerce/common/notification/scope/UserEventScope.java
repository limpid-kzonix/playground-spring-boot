package com.omniesoft.commerce.common.notification.scope;

import com.omniesoft.commerce.persistence.entity.enums.NotifTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.security.Principal;
import java.util.UUID;

/**
 * Generates by user
 * Target admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEventScope extends EventScope {
    private UUID organizationReceiver;

    private UserEventScope(Principal emitter, String destanation, UUID orgId) {
        super(emitter, destanation, NotifTarget.ADMIN);
        this.organizationReceiver = orgId;
    }

    public static UserEventScope of(Principal emitter, String destanation, UUID orgId) {
        return new UserEventScope(emitter, destanation, orgId);
    }
}
