/*
 * Copyright (c) 2017.
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.common.handler.exception.custom.enums;

public enum OwnerModuleErrorCodes implements ErrorCode {
    ORGANIZATION_TIMESHEET_VALIDATING_ERROR_BY_BREAK_HOURS(3013),
    ORGANIZATION_TIMESHEET_VALIDATING_ERROR_BY_WORK_HOURS(3012),
    ACTION_NOT_ALLOWED_ORDER_IS_EXIST_BY_TIME_SHEET(3011),
    DISCOUNT_DATE_CONFIGURATION_INCORRECT(3010),
    PERMISSION_DENIED(3009),
    CLIENT_PROFILE_NOT_EXIST(3008),
    SERVICE_TIMESHEET_VALIDATING_ERROR(3007),
    SERVICE_NOT_CONFIGURED(3006),
    ACTION_NOT_ALLOWED(3005),
    DISCOUNT_PERSONAL_STATUS_VIOLATION(3004),
    ORDER_STATUS_NOT_CHANGEABLE(3003),
    DISCOUNT_VIOLATION_LIMITS(3002),
    ORDER_TIMESHEET_CONFLICT(3001);


    private Integer code;

    OwnerModuleErrorCodes(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {

        return code;
    }
}
