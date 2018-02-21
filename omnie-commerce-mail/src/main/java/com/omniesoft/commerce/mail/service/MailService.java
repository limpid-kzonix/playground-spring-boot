package com.omniesoft.commerce.mail.service;

import javax.mail.MessagingException;

public interface MailService {
    /**
     * Send email with confirmation token for activation of account. .
     *
     * @param recipientAddress String : Email of recipient
     * @param subject          String : Subject of email
     * @param text             String : Message content of email
     */
    void sendMessage(String recipientAddress, String subject, String text) throws MessagingException;

}
