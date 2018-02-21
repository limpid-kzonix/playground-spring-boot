package com.omniesoft.commerce.common.ws.statistic.impl.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogPayload {
    private UUID userId;
    private LocalDateTime dateTime;
}
