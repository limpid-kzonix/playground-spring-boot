package com.omniesoft.commerce.notification.controllers.receiver;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/receiver", produces = MediaType.APPLICATION_JSON_VALUE)
public abstract class AbstractNotificationController {

}
