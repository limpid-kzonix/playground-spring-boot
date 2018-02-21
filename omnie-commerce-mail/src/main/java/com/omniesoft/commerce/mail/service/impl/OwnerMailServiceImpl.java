package com.omniesoft.commerce.mail.service.impl;

import com.omniesoft.commerce.mail.message.MailMessageBuilder;
import com.omniesoft.commerce.mail.service.MailService;
import com.omniesoft.commerce.mail.service.OwnerMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class OwnerMailServiceImpl implements OwnerMailService {

    @Autowired
    private MailService mailService;

    @Autowired
    private MailMessageBuilder builder;

    @Override
    public void sendInvite(String recipientAddress, String confirmationLink) throws MessagingException {
        mailService.sendMessage(recipientAddress, "", builder.build("", "", confirmationLink));
    }

    @Override
    public void sendResetPasswordMessage(String recipientAddress, String code) throws MessagingException {
        mailService.sendMessage(recipientAddress, "", builder.build("", "", code));

    }

    @Override
    public void sendChangeEmailMessage(String recipientAddress, String newEmailAddress) throws MessagingException {
        mailService.sendMessage(recipientAddress, "", builder.build("", "", ""));
    }
}
