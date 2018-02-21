package com.omniesoft.commerce.persistence.entity.enums;

/**
 * @author Vitalii Martynovskyi
 * @since 27.09.17
 */
public enum OrderStatus {

    PENDING_FOR_USER,

    PENDING_FOR_ADMIN,

    CONFIRM_BY_USER,

    CONFIRM_BY_ADMIN,

    CANCEL_BY_ADMIN,

    CANCEL_BY_USER,

    FAIL_BY_USER,

    DONE;

}
