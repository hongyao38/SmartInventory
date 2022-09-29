package com.smartinventory.auth;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartinventory.email.EmailSenderService;
import com.smartinventory.exceptions.token.EmailAlreadyVerifiedException;
import com.smartinventory.exceptions.token.InvalidTokenException;
import com.smartinventory.security.token.ConfirmationToken;
import com.smartinventory.security.token.ConfirmationTokenService;
import com.smartinventory.user.*;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final ConfirmationTokenService tokenService;
    private final EmailSenderService emailSender;

    /*
     * Takes in AuthRequest object and converts it to new User
     * Registers the new user, and sends out a confirmation email
     * Return token string for new user
     */
    public String register(AuthRequest request) {

        String reqEmail = request.getEmail();
        String reqUsername = request.getUsername();

        // Register user and get token
        String token = userService.registerUser(
            new User(reqEmail, reqUsername, request.getPassword())
        );

        // Form email body
        String confirmationLink = "localhost:8080/api/v1/registration/confirm?token=" + token;
        String emailBody = String.format("Hi, %s!%n%n" +
                                        "Confirm your email: %s\n\n" + 
                                        "Link will expire in 15 minutes.%n", reqUsername, confirmationLink);

        // Send email
        emailSender.send(reqEmail, emailBody, "SmartInventory: Confirm Your Email");
        return token;
    }

    /*
     * Takes in token string, looks for confirmation token 
     * Uses confirmation token to enable User (verify email)
     * returns "confirmed" if successful
     */
    @Transactional
    public String confirmToken(String token) {

        // Retrieve confirmation token from db (if exists)
        ConfirmationToken confirmationToken = tokenService.getToken(token)
            .orElseThrow(() -> new InvalidTokenException("Token not found!"));

        // Check if token has already been used to verify email
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyVerifiedException();
        }

        // Check if token has expired
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token not found, already expired!");
        }

        tokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }


    /*
     * 
     */
    public User forgetUserPassword(AuthRequest request) {
        return userService.forgetUserPassword(
            new User(request.getEmail(), request.getUsername(), request.getPassword()));
    }
    
}
