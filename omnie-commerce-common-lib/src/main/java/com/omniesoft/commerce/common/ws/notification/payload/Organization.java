package com.omniesoft.commerce.common.ws.notification.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    private UUID id;

    private String name;

    private String logoId;
}
