package com.omniesoft.commerce.mail.service.impl;

import com.omniesoft.commerce.mail.message.MailMessageBuilder;
import com.omniesoft.commerce.mail.service.AccountMailService;
import com.omniesoft.commerce.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Slf4j
@Service
public class AccountMailServiceImpl implements AccountMailService {

    @Autowired
    private MailService mailService;

    @Autowired
    private MailMessageBuilder builder;

    @Override
    public void sendRegistrationMessage(String recipientAddress, String confirmationLink) throws MessagingException {
        logSendingEmail(recipientAddress, confirmationLink);
        mailService.sendMessage(recipientAddress, "Реєстрація", builder.build("Ласкаво просимо", "Реєстація", confirmationLink));
    }

    @Override
    public void sendResetPasswordMessage(String recipientAddress, Integer code) throws MessagingException {
        logSendingEmail(recipientAddress, String.valueOf(code));
        mailService.sendMessage(recipientAddress, "", builder.build("", "", code));
    }

    @Override
    public void sendChangeEmailMessage(String recipientAddress, String newEmailAddress) throws MessagingException {
        logSendingEmail(recipientAddress, newEmailAddress);
        mailService.sendMessage(recipientAddress, "", builder.build("", "", ""));
    }


    @Override
    public void sendPasswordChangeNotify(String recipientAddress, String userName) throws MessagingException {
        logSendingEmail(recipientAddress, userName);
        mailService.sendMessage(recipientAddress, "Зміна паролю", builder.build(
                "Сповіщення",
                "Пароль змінено",
                userName, LocalDateTime.now()
        ));
    }

    private void logSendingEmail(String recipient, String emailPayload) {
        log.info("Email for recipient {} with payload [ {} ] will send", recipient, emailPayload);
    }
}
