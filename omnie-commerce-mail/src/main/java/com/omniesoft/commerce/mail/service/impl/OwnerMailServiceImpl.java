package com.omniesoft.commerce.mail.service.impl;

import com.omniesoft.commerce.mail.message.MailMessageBuilder;
import com.omniesoft.commerce.mail.service.MailService;
import com.omniesoft.commerce.mail.service.OwnerMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Service
public class OwnerMailServiceImpl implements OwnerMailService {

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
    public void sendInvite(String recipientAddress, String confirmationLink) throws MessagingException {
        mailService.sendMessage(recipientAddress, "", builder.build("", "", confirmationLink, LocalDateTime.now()));
    }

    @Override
    public void sendResetPasswordMessage(String recipientAddress, String code) throws MessagingException {
        mailService.sendMessage(recipientAddress, "", builder.build("", "", code, LocalDateTime.now()));

    }

    @Override
    public void sendChangeEmailMessage(String recipientAddress, String newEmailAddress) throws MessagingException {
        mailService.sendMessage(recipientAddress, "", builder.build("", "", "", LocalDateTime.now()));
    }
}
