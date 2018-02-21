package com.omniesoft.commerce.user.controller.organization.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTimingPayload {

    @NotNull
    private Boolean minDuration;

    @NotNull
    private Long durationMillis;

    @NotNull
    private Long pauseMillis;

    @Min(1)
    @NotNull
    private Integer slotCount;

    @NotNull
    private Double maxDiscount;

    @NotNull
    private LocalDateTime activeFrom;

    @NotNull
    private LocalDateTime createTime;
}
