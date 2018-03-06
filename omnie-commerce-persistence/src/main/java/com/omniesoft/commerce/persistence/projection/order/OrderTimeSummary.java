package com.omniesoft.commerce.persistence.projection.order;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderTimeSummary {

    UUID getId();

    LocalDateTime getStart();

    LocalDateTime getEnd();

    LocalDateTime getCreateTime();

}
