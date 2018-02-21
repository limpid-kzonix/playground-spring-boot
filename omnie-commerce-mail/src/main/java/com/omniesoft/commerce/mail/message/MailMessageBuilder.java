package com.omniesoft.commerce.mail.message;

import java.time.LocalDateTime;

public interface MailMessageBuilder {

    /**
     *
     * */
    String build(String header, String title, String link);

    /**
     *
     * */
    String build(String header, String title, Integer code);

    /**
     *
     * */
    String build(String header, String title, String message, String username, String organization);

    /**
     *
     * */
    String build(String header, String title, String username, LocalDateTime dateTime);
}
