package com.smartinventory.user;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartinventory.exceptions.token.InvalidTokenException;
import com.smartinventory.exceptions.user.*;
import com.smartinventory.security.ConfirmationToken;
import com.smartinventory.security.ConfirmationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserIdNotFoundException("ID: " + id));
    }

    /*
     * Create new exception for username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserEmailNotFoundException(username));
    }

    /*
     * Finds user by email
     * If email does not exist,
     * throws UserEmailNotFoundException
     */
    public User getUserByEmail(String email) throws UserEmailNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new UserEmailNotFoundException(email)
        );
    }

    public String getEmail(String username) {
        return userRepository.findEmailByUsername(username);
    }

    /*
     * Finds user by email, and updates password
     * If email does not exist,
     * throws UserEmailNotFoundException
     */
    public User updateUserPassword(String email, String password) throws UserEmailNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email).get();
        if (user == null) {
            throw new UserEmailNotFoundException(email);
        }
        user.setPassword(password);
        return userRepository.save(user);
    }

    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    /*
     * Finds existing users with desired username, add new user if no user found
     * If existing user found,
     * throws UsernameTakenException
     */
    // public User addUser(User user) throws UsernameTakenException {
    //     String newUsername = user.getUsername();

    //     if (userRepository.findByUsername(newUsername).isPresent()) {
    //         throw new UsernameTakenException(newUsername);
    //     }
    //     return userRepository.save(user);
    // }

    public String registerUser(User user) {

        String email = user.getEmail();
        String username = user.getUsername();

        // If email is taken, throw exception
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new UserEmailTakenException(email);
        }

        // If username is taken, throw exception
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameTakenException(username);
        }

        // Encrypt and set password
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Add user to database
        userRepository.save(user);

        // TODO: Send confirmation token

        return "it works (registerUser)";
    }

    
    /*
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
        if (token.isEmpty()) {
            throw new InvalidTokenException("Invalid confirmation token!");
        }
        String email = token.get().getUser().getEmail();
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        if(user.isEmpty()){
            throw new UserEmailNotFoundException(email);
        }
        return updateUserPassword(email, password);
    }

}
