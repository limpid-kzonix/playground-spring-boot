package com.omniesoft.commerce.persistence.entity.enums;

public enum NotifType {
    // in case if you add new type of notification not related to orders
    // you have to add some logic to checking NotifEntity:item relation
    // for example com.omniesoft.commerce.notification.service.NotifService.getOrder()
    ORDER_NEW,
    ORDER_CHANGE,
    ORDER_CONFIRM,
    ORDER_CANCEL,
    ORDER_DONE,
    ORDER_FAIL;
}
