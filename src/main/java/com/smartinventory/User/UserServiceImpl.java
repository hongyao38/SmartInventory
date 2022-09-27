package com.smartinventory.user;

import java.util.*;
import com.smartinventory.exceptions.user.*;

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

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private ConfirmationTokenRepository tokenRepository;

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    /*
     * Finds user by email
     * If email does not exist,
     * throws UserEmailNotFoundException
     */
    @Override
    public User getUserByEmail(String email) throws UserEmailNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new UserEmailNotFoundException(email)
        );
    }

    @Override
    public String getEmail(String username) {
        return userRepository.findEmailByUsername(username);
    }

    /*
     * Finds user by email, and updates password
     * If email does not exist,
     * throws UserEmailNotFoundException
     */
    @Override
    public User updateUserPassword(String email, User newUser) throws UserEmailNotFoundException {
        // return userRepository.findByEmailIgnoreCase(email).map(user -> {
        //     user.setPassword(newUser.getPassword());
        //     return userRepository.save(user);
        // }).orElseThrow(() -> new UserEmailNotFoundException(email));

        User user = userRepository.findByEmailIgnoreCase(email).get();
        if (user == null) {
            throw new UserEmailNotFoundException(email);
        }
        user.setPassword(newUser.getPassword());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteById(email);
    }

    /*
     * Finds existing users with desired username, add new user if no user found
     * If existing user found,
     * throws UsernameTakenException
     */
    public User addUser(User user) throws UsernameTakenException {
        String newUsername = user.getUsername();

        if (!userRepository.findByUsername(newUsername).isEmpty()) {
            throw new UsernameTakenException(newUsername);
        }
        return userRepository.save(user);
    }

    
    /*
     * NOTE: DO WE WANT TO TAKE IN USER OBJECT?
     * Takes in user object, looks for user via user email
     * Sends reset password email if user found
     * If user not found,
     * throws UserEmailNotFoundException
     */
    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public User forgetUserPassword(User user) throws UserEmailNotFoundException {
        Optional<User> existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());

        if (existingUser.isEmpty()) {
            throw new UserEmailNotFoundException(user.getEmail());
        }

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
    }

    @RequestMapping(value = "/confirm-reset", method = { RequestMethod.GET, RequestMethod.POST })
    public User validateResetToken(@RequestParam("token") String confirmationToken, String password) {
        Optional<ConfirmationToken> token = tokenRepository.findByConfirmationToken(confirmationToken);
        if (!token.isEmpty()) {
            Optional<User> user = userRepository.findByEmailIgnoreCase(token.get().getUser().getEmail());
            if(!user.isEmpty()){
                User updatedUser = updateUserPassword(user.get().getEmail(), new User(user.get().getUsername(), user.get().getUsername(), password));
                return updatedUser;

            }else{
                return null;
            }
            
        } else {
            return null;
        }

    }

}
