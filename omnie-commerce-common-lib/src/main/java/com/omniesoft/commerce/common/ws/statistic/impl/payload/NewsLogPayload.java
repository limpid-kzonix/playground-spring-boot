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
public class NewsLogPayload {

    private UUID userId;
    private LocalDateTime dateTime;
    private List<UUID> news;
}
