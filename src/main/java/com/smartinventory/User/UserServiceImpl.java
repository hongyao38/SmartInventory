package com.smartinventory.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.smartinventory.security.ConfirmationToken;
import com.smartinventory.security.ConfirmationTokenRepository;
import com.smartinventory.security.EmailSenderService;

import org.springframework.mail.SimpleMailMessage;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private ConfirmationTokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, ConfirmationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findById(email).orElse(null);
    }

    @Override
    public String getEmail(String username) {
        return userRepository.findEmailByUsername(username);
    }

    @Override
    public User updateUser(String email, User newUser) {
        return userRepository.findByEmailIgnoreCase(email).map(user -> {
            user.setPassword(newUser.getPassword());
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteById(email);
    }

    /*
     * Create new exceptions to throw for cases
     * where users are not added successfully
     * Update UserServiceImpl accordingly
     */
    public User addUser(User user) {
        if (!userRepository.findByUsername(user.getUsername()).isEmpty()) {
            return null;
        }
        return userRepository.save(user);
    }

    
    

    

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public User forgetUserPassword(User user) {
        Optional<User> existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());

        if (!existingUser.isEmpty()) {

            // Recipient's email ID needs to be mentioned.
            String to = existingUser.get().getEmail();

            // Sender's email ID needs to be mentioned
            String from = "smartinventoryCS203@gmail.com";

            // Assuming you are sending email from through gmails smtp
            String host = "smtp.gmail.com";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Get the Session object.// and pass username and password
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication("smartinventoryCS203@gmail.com", "cdnurpquapkwlvtz");

                }

            });

            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser.get());
            System.out.println(confirmationToken.getConfirmationToken());

            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("Complete Password Reset!");

                // Now set the actual message
                // Need to change the html page to the actual page
                message.setText("To complete the password reset process, please click here: "
                        + "localhost:8080/confirm-reset?token="
                        + confirmationToken.getConfirmationToken());

                System.out.println("sending...");
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
            return existingUser.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/confirm-reset", method = { RequestMethod.GET, RequestMethod.POST })
    public User validateResetToken(@RequestParam("token") String confirmationToken, String password) {
        Optional<ConfirmationToken> token = tokenRepository.findByConfirmationToken(confirmationToken);
        if (!token.isEmpty()) {
            Optional<User> user = userRepository.findByEmailIgnoreCase(token.get().getUser().getEmail());
            if(!user.isEmpty()){
                User updatedUser = updateUser(user.get().getEmail(), new User(user.get().getUsername(), user.get().getUsername(), password));
                return updatedUser;

            }else{
                return null;
            }
            
        } else {
            return null;
        }

    }

}
