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

public enum NotificationModuleErrorCodes implements ErrorCode {
    NOT_CONFIRMED_ACCOUNT(0),
    SETTINGS_NOT_EXIST(0),

    USER_EXIST(0),
    USER_NOT_EXIST(0),
    PROFILE_NOT_EXIST(0);


    private Integer code;

    NotificationModuleErrorCodes(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {

        return code;
    }
}
