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
    SOCIAL_BIRTHDATE_INCORRECT(4916),
    SOCIAL_GENDER_INCORRECT(4915),
    SOCIAL_GOOGLE_SIGN_IN_ERROR(4914),
    SOCIAL_FACEBOOK_SIGN_IN_ERROR(4913),
    SOCIAL_GOOGLE_PROFILE_NOT_EXIST(4912),
    SOCIAL_FACEBOOK_PROFILE_NOT_EXIST(4911),
    SOCIAL_ID_INCORRECT(4910),
    SOCIAL_EMAIL_INCORRECT(4909),
    SOCIAL_FIRST_NAME_INCORRECT(4908),
    SOCIAL_LAST_NAME_INCORRECT(4907),
    SOCIAL_CANT_FETCH(4906),
    SOCIAL_NOT_CONNECTED(4905),
    SOCIAL_CONNECT_ERROR(4904),
    INVALID_EMAIL_TOKEN(4903),
    INVALID_PASSWORD(4902),
    CLIENT_CREDENTIALS(4901);


    private Integer code;

    SecurityModuleErrorCodes(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {

        return code;
    }
}
