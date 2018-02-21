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

package com.omniesoft.commerce.user.service.util.event.conversation;

import com.omniesoft.commerce.common.ws.notification.NotificationRestTemplate;
import com.omniesoft.commerce.common.ws.notification.payload.ConversationMessage;
import com.omniesoft.commerce.common.ws.notification.payload.ConversationMessageFactory;
import com.omniesoft.commerce.persistence.entity.conversation.ConversationEntity;
import com.omniesoft.commerce.persistence.entity.conversation.MessageEntity;
import com.omniesoft.commerce.user.service.util.event.NotificationServiceAbstractClient;

public abstract class NotificationConversationAbstraction extends NotificationServiceAbstractClient {

    public NotificationConversationAbstraction(
            NotificationRestTemplate notificationRestTemplate) {

        super(notificationRestTemplate);
    }


    protected void send(ConversationEntity conversationEntity) {

        ConversationMessage compact = ConversationMessageFactory.compact(conversationEntity);
        notificationRestTemplate.<ConversationMessage>sendReviewNotification(compact);
    }

    protected void send(MessageEntity messageEntity) {

        ConversationMessage compact = ConversationMessageFactory.compact(messageEntity);
        notificationRestTemplate.sendReviewNotification(compact);
    }
}
