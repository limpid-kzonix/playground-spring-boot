package com.omniesoft.commerce.mail.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface AccountMailService {
    /**
     * Send email with confirmation token for activation of account. .
     *
     * @param recipientAddress String : Email of recipient
     * @param confirmationLink String : Link for handling user account confirmation
     */
    void sendRegistrationMessage(String recipientAddress, String confirmationToken) throws MessagingException;

    /**
     * Send email with code for using in change password of account. .
     *
     * @param recipientAddress String : Email of recipient
     * @param code             String : Link for handling user account confirmation
     */
    void sendResetPasswordMessage(String recipientAddress, Integer code) throws MessagingException;

    /**
     * Send email with confirmation of change email of account. .
     *
     * @param recipientAddress String : Email of recipient
     * @param newEmailAddress  String : Link for handling user account confirmation
     */
    void sendChangeEmailMessage(String recipientAddress, String newEmailAddress) throws MessagingException;

    void sendPasswordChangeNotify(String recipientAddress, String userName) throws MessagingException;
}
