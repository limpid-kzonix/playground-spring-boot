package com.omniesoft.commerce.notification.event.scope;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.security.Principal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEventScope extends EventScope {
    private UUID organizationReceiver;

    private UserEventScope(Principal emitter, String destanation, UUID orgId) {
        super(emitter, destanation);
        this.organizationReceiver = orgId;
    }

    public static UserEventScope of(Principal emitter, String destanation, UUID orgId) {
        return new UserEventScope(emitter, destanation, orgId);
    }
}
