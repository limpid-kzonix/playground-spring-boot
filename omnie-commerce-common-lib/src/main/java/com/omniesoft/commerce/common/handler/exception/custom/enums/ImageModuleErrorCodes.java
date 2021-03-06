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

public enum ImageModuleErrorCodes implements ErrorCode {
    UNSUPPORTED_IMAGE_MIME_TYPE(8106),
    GENERAL_IMAGE_ERROR(8105),
    RECEIVED_IMAGE_IS_CORRUPTED(8104),
    IMAGE_SERVICE_UNAVAILABLE(8103),
    IMAGE_PROCESSING_ERROR(8102),
    CAN_NOT_FETCH_IMAGE_FROM_DB(8101);


    private Integer code;

    ImageModuleErrorCodes(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {

        return code;
    }
}
