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

package com.omniesoft.commerce.common.handler.exception.custom;

import com.omniesoft.commerce.common.handler.exception.custom.enums.ErrorCode;

public class UsefulException extends WrappedRuntimeException {

    public UsefulException() {

    }

    public UsefulException(ErrorCode exceptionCode) {

        super(exceptionCode);
    }

    public UsefulException(String message) {

        super(message);
    }

    public UsefulException(String message, String debugMessage) {

        super(message, debugMessage);
    }

    public UsefulException(String message,
                           ErrorCode exceptionCode) {

        super(message, exceptionCode);
    }

    public UsefulException(String message, String debugMessage,
                           ErrorCode exceptionCode) {

        super(message, debugMessage, exceptionCode);
    }

    public UsefulException(String message, Throwable cause) {

        super(message, cause);
    }

    public UsefulException(Throwable cause) {

        super(cause);
    }

    public UsefulException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }
}
