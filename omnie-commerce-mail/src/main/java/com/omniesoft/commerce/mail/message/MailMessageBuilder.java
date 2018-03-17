package com.omniesoft.commerce.mail.message;

import java.net.URI;
import java.time.LocalDateTime;

public interface MailMessageBuilder {

    /**
     *
     * */
    String build(String header, String title, URI link, LocalDateTime localDateTime);

    /**
     *
     * */
    String build(String header, String title, Integer code, LocalDateTime localDateTime);

    /**
     *
     * */
    String build(String header, String title, String message, LocalDateTime localDateTime);

    /**
     *
     * */
    String build(String header, String title, String message, URI link, LocalDateTime localDateTime);
}

