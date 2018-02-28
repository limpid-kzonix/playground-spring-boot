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

public enum SecurityModuleErrorCodes implements ErrorCode {
    SOCIAL_BIRTHDATE_INCORRECT(4116),
    SOCIAL_GENDER_INCORRECT(4115),
    SOCIAL_GOOGLE_SIGN_IN_ERROR(4114),
    SOCIAL_FACEBOOK_SIGN_IN_ERROR(4113),
    SOCIAL_GOOGLE_PROFILE_NOT_EXIST(4112),
    SOCIAL_FACEBOOK_PROFILE_NOT_EXIST(4111),
    SOCIAL_ID_INCORRECT(4110),
    SOCIAL_EMAIL_INCORRECT(4109),
    SOCIAL_FIRST_NAME_INCORRECT(4108),
    SOCIAL_LAST_NAME_INCORRECT(4107),
    SOCIAL_CANT_FETCH(4106),
    SOCIAL_NOT_CONNECTED(4105),
    SOCIAL_CONNECT_ERROR(4104),
    INVALID_EMAIL_TOKEN(4103),
    INVALID_PASSWORD(4102),
    INVALID_CLIENT_CREDENTIALS(4101);

    private Integer code;

    SecurityModuleErrorCodes(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {

        return code;
    }
}
