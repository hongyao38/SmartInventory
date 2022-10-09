package com.smartinventory.email;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("emailSenderService")
public class EmailSenderService implements EmailSender {

    private final JavaMailSender mailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    public String readHTML(String filepath) {
        StringBuilder htmlBuilder = new StringBuilder();
        try {
            BufferedReader file = new BufferedReader(new FileReader(filepath));
            String html;
            while ((html = file.readLine()) != null) {
                htmlBuilder.append(html);
            }
            file.close();
        } catch (IOException e) {
            LOGGER.error("File not found");
        }
        return htmlBuilder.toString();
    }

    @Override
    @Async
    public void send(String to, String emailBody, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            // Configure email sender
            helper.setText(emailBody, true);
            helper.setTo(to);
            helper.setFrom("smartinventoryCS203@gmail.com");
            helper.setSubject(subject);

            // Send the email
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }

    // @Async
    // public void sendEmail(SimpleMailMessage email) {
    //     mailSender.send(email);
    // }
}
