package com.omniesoft.commerce.user.service.util.event.account.events;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class OnEmailChangeEvent extends ApplicationEvent {
    @Getter
    private UserEntity userEntity;
    @Getter
    private String newEmail;
    @Getter
    private String token;


    public OnEmailChangeEvent(UserEntity userEntity, String newEmail, String token) {
        super(userEntity);
        this.userEntity = userEntity;
        this.newEmail = newEmail;
        this.token = token;
    }

}
