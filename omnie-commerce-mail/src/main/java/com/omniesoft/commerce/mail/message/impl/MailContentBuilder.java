package com.omniesoft.commerce.mail.message.impl;

import com.omniesoft.commerce.mail.message.MailMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
     * @param header String :
     * @param title  String :
     * @param link   String :
     */
    @Override
    public String build(String header, String title, String link) {
        Context context = new Context();
        initializeContext(context, header, title, link, null, null);
        return templateEngine.process(TEMPLATE_NAME, context);
    }

    /**
     * Build message with header, title and code
     *
     * @param header String :
     * @param title  String :
     * @param code   Integer :
     */
    @Override
    public String build(String header, String title, Integer code) {
        Context context = new Context();
        initializeContext(context, header, title, null, null, code);
        return templateEngine.process(TEMPLATE_NAME, context);
    }

    /**
     * Build message with header, title and code
     *
     * @param header       String :
     * @param title        String :
     * @param message      String :
     * @param username     String :
     * @param organization String :
     */
    @Override
    public String build(String header, String title, String message, String username, String organization) {
        Context context = new Context();
        String info = String.format("%s %s %s", username, message, organization);
        initializeContext(context, header, title, null, info, null);
        return templateEngine.process(TEMPLATE_NAME, context);
    }

    @Override
    public String build(String header, String title, String username, LocalDateTime dateTime) {
        Context context = new Context();
        String notify = String.format("В обіковому записі %s було змінено пароль ( %s )", username, dateTime.toString());
        initializeContext(context, header, title, null, notify, null);
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
