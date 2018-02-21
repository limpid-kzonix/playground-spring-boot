package com.omniesoft.commerce.common.ws.statistic.impl.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLogPayload {
    private UUID userId;
    private List<UUID> organizations;
    private List<UUID> services;
    private LocalDateTime dateTime;
}
