package com.omniesoft.commerce.user.service.util.event.account.events;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class OnEmailChangedEvent extends ApplicationEvent {
    @Getter
    private UserEntity userEntity;
    @Getter
    private String newEmail;

    public OnEmailChangedEvent(UserEntity userEntity, String newEmail) {
        super(userEntity);
        this.userEntity = userEntity;
        this.newEmail = newEmail;
    }

}
