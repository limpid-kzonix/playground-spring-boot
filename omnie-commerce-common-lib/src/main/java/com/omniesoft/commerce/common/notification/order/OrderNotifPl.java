package com.omniesoft.commerce.common.notification.order;

import com.omniesoft.commerce.common.notification.NotifType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderNotifPl {
    private UUID id;
    private UUID orderId;
    private NotifType type;

}
