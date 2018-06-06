package com.omniesoft.commerce.common.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifMessage<T> {
    private UUID id;
    private NotifType type;
    private T content;
}
