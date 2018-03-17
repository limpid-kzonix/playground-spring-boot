package com.omniesoft.commerce.mail.message.impl;

import com.omniesoft.commerce.mail.message.MailMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class MailContentBuilder implements MailMessageBuilder {
    private static final String TEMPLATE_NAME = "mailTemplate";
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    /**
     * Build message with header, title and link
     *
     * @param header        String :
     * @param title         String :
     * @param link          String :
     * @param localDateTime LocalDateTime
     */
    @Override
    public String build(String header, String title, URI link, LocalDateTime localDateTime) {
        Context context = new Context();
        initializeContext(context, header, title, link.toASCIIString(), null, null);
        return templateEngine.process(TEMPLATE_NAME, context);
    }

    /**
     * Build message with header, title and code
     *
     * @param header        String :
     * @param title         String :
     * @param code          Integer :
     * @param localDateTime LocalDateTime
     */
    @Override
    public String build(String header, String title, Integer code, LocalDateTime localDateTime) {
        Context context = new Context();
        initializeContext(context, header, title, null, null, code);
        return templateEngine.process(TEMPLATE_NAME, context);
    }

    /**
     * Build message with header, title and code
     *
     * @param header        String :
     * @param title         String :
     * @param message       String :
     * @param localDateTime LocalDateTime
     */
    @Override
    public String build(String header, String title, String message, LocalDateTime localDateTime) {
        Context context = new Context();
        initializeContext(context, header, title, null, message, null);
        return templateEngine.process(TEMPLATE_NAME, context);
    }

    @Override
    public String build(String header, String title, String message, URI link, LocalDateTime localDateTime) {
        Context context = new Context();
        initializeContext(context, header, title, link.toASCIIString(), message, null);
        return templateEngine.process(TEMPLATE_NAME, context);
    }

    /**
     *
     * */
    private void initializeContext(Context context,
                                   String header,
                                   String title,
                                   String link,
                                   String info,
                                   Integer code) {
        context.setVariable("welcome", header);
        context.setVariable("title", title);
        context.setVariable("code", code);
        context.setVariable("info", info);
        context.setVariable("link", link);
    }
}
