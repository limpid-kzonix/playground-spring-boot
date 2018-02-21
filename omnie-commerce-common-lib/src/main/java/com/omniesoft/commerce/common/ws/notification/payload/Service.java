package com.omniesoft.commerce.common.ws.notification.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Service {

    private UUID id;

    private String name;

    private String logoId;

    private Organization organization;
}
