package com.omniesoft.commerce.mail.service;

import javax.mail.MessagingException;

public interface OwnerMailService {
    /**
     * Send email with confirmation token for activation of account. .
     *
     * @param recipientAddress String : Email of recipient
     * @param confirmationLink String: Link for handling user account confirmation
     */
    void sendInvite(String recipientAddress, String confirmationLink) throws MessagingException;

    /**
     * Send email with code for using in change password of account. .
     *
     * @param recipientAddress String : Email of recipient
     * @param code             String: Link for handling user account confirmation
     */
    void sendResetPasswordMessage(String recipientAddress, String code) throws MessagingException;

    /**
     * Send email with confirmation of change email of account. .
     *
     * @param recipientAddress String : Email of recipient
     * @param newEmailAddress  String: Link for handling user account confirmation
     */
    void sendChangeEmailMessage(String recipientAddress, String newEmailAddress) throws MessagingException;
}
