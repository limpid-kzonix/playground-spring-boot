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

/**
 * Description: 5xxx code group
 */
public enum InternalErrorCodes implements ErrorCode {
    LOAD_BALANCER_PROXY_ERROR(5116),
    ASYNC_REQUEST_TIMEOUT(5115),
    INCORRECT_MULTIPART_ID(5114),
    MISSING_REQUEST_PATH_VARIABLE(5113),
    HTTP_METHOD_TYPE_NOT_SUPPORTED(5112),
    RESOURCE_NOT_FOUND(5111),
    MISSING_SERVLET_REQUEST_PARAMETER(5110),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(5109),
    METHOD_ARGUMENT_NOT_VALID(5108), // Triggered when an object fails @Valid validation : HibernateValidator exception
    EMPTY_RESULT_DATA_ACCESS(5107),
    METHOD_CUSTOM_PARAMS_VALIDATION(5106),
    CONSTRAINT_VALIDATION(5105),
    DATA_INTEGRITY_VIOLATION(5104),
    ENTITY_NOT_FOUND(5103),
    METHOD_ARGUMENT_TYPE_MISMATCH(5102),

    INTERNAL(5101);


    private Integer code;

    InternalErrorCodes(Integer code) {

        this.code = code;
    }

    @Override
    public Integer getCode() {

        return code;
    }
}
