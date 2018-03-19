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

public enum UserModuleErrorCodes implements ErrorCode {
    EMAIL_NOT_ALLOWED(2108),
    NOT_ALLOWED_CHANGE_OPERATION(2107),
    NOT_ALLOWED_CREATE_REVIEW(2106),
    NOT_ALLOWED_EDIT_REVIEW(2105),
    HANDBOOK_ITEM_NOT_EXIST(2104),
    USER_NOT_ACTIVE(2103),
    SETTINGS_NOT_EXIST(2102),
    USER_NOT_EXIST(2101);

    private Integer code;

    UserModuleErrorCodes(Integer code) {

        this.code = code;
    }

    @Override
    public Integer getCode() {

        return code;
    }
}
