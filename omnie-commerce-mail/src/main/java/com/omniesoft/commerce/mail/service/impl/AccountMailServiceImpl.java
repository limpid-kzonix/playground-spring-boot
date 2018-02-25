package com.omniesoft.commerce.mail.service.impl;

import com.omniesoft.commerce.mail.message.MailMessageBuilder;
import com.omniesoft.commerce.mail.service.AccountMailService;
import com.omniesoft.commerce.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@Service
public class AccountMailServiceImpl implements AccountMailService {

    private MailService mailService;
    private MailMessageBuilder builder;

    @Required
    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Required
    @Autowired
    public void setBuilder(MailMessageBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void sendRegistrationMessage(String recipientAddress, String confirmationToken) throws MessagingException {
        logSendingEmail(recipientAddress, confirmationToken);
        mailService.sendMessage(recipientAddress, "Реєстрація", builder.build("Ласкаво просимо", "Реєстація", getConfimationLinkByToken(confirmationToken), LocalDateTime.now()));
    }


    @Override
    public void sendResetPasswordMessage(String recipientAddress, Integer code) throws MessagingException {
        logSendingEmail(recipientAddress, String.valueOf(code));
        mailService.sendMessage(recipientAddress, "", builder.build("", "", code, LocalDateTime.now()));
    }

    @Override
    public void sendChangeEmailMessage(String recipientAddress, String newEmailAddress) throws MessagingException {
        logSendingEmail(recipientAddress, newEmailAddress);
        mailService.sendMessage(recipientAddress, "", builder.build("", "", "", LocalDateTime.now()));
    }


    @Override
    public void sendPasswordChangeNotify(String recipientAddress, String userName) throws MessagingException {
        logSendingEmail(recipientAddress, userName);
        mailService.sendMessage(recipientAddress, "Зміна паролю", builder.build(
                "Сповіщення",
                "Пароль змінено",
                "", LocalDateTime.now()
        ));
    }

    private void logSendingEmail(String recipient, String emailPayload) {
        log.info("Email for recipient {} with payload [ {} ] will send", recipient, emailPayload);
    }


    private URI getConfimationLinkByToken(String token) {
        return URI.create("http://" + ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteHost() + ":9999/omnie-user/api/account/confirmation/" + token);
    }
}
