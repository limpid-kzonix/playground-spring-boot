package com.omniesoft.commerce.mail.service.impl;

import com.omniesoft.commerce.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    @Async
    public void sendMessage(String recipientAddress, String subject, String text) throws MessagingException {
        MimeMessage mail = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(recipientAddress);
        helper.setSubject(subject);
        helper.setText(text, true);
        log.info("Sending prepared email to {} ", recipientAddress);
        emailSender.send(mail);

    }
}
