package com.omniesoft;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.omniesoft.commerce.user.UserApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MailServiceTest {

    private JavaMailSenderImpl emailSender;

    private GreenMail smtpServer;

    @Before
    public void setUp() throws Exception {
        ServerSetup setup = new ServerSetup(5876, "localhost", "smtp");
        smtpServer = new GreenMail(setup);
        emailSender = new JavaMailSenderImpl();
        emailSender.setHost("localhost");
        emailSender.setPort(5876);
        smtpServer.start();
    }


    @Test
    public void mailSend() throws IOException, MessagingException {
        sendMessage();
        receiveMessages();
    }

    private void sendMessage() {
        log.info("Send message");
        SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
        for (int i = 0; i < 10; i++) {
            emailSender.send(simpleMailMessage);
        }
        assertTrue(smtpServer.waitForIncomingEmail(5000, 1));
    }

    private SimpleMailMessage getSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Message Subject");
        simpleMailMessage.setText("Message Content");
        simpleMailMessage.setTo("test@receiver.icegreen");
        return simpleMailMessage;
    }

    private void receiveMessages() throws MessagingException, IOException {
        log.info("Count {}", smtpServer.getReceivedMessages().length);
        for (MimeMessage mimeMessage : smtpServer.getReceivedMessages()) {
            log.info(
                    "{} {} received message with subject '{}' and content '{}'",
                    mimeMessage.getContentType(),
                    mimeMessage.getAllRecipients(),
                    mimeMessage.getSubject(),
                    mimeMessage.getContent()
            );
        }
    }


    @After
    public void tearDown() throws Exception {
        smtpServer.stop();

    }
}
