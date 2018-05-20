package com.omniesoft.commerce.common.ws.notification.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;

    private String firstName;

    private String lastName;

    private String imageId;

    private String login;

    private String email;

}
