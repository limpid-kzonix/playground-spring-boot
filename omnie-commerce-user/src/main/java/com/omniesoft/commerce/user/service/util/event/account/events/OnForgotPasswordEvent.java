/*
 * Copyright (c)  2017
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.user.service.util.event.account.events;

import com.omniesoft.commerce.persistence.entity.account.UserPasswordResetEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class OnForgotPasswordEvent extends ApplicationEvent {
    @Getter
    private UserPasswordResetEntity passwordResetEntity;

    public OnForgotPasswordEvent(UserPasswordResetEntity source) {
        super(source);
        this.passwordResetEntity = source;
    }
}
