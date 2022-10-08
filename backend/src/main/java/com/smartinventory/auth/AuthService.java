package com.smartinventory.auth;

import com.smartinventory.auth.dto.*;

import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartinventory.email.EmailSenderService;
import com.smartinventory.exceptions.token.EmailAlreadyVerifiedException;
import com.smartinventory.exceptions.token.InvalidTokenException;
import com.smartinventory.exceptions.user.PasswordNotMatchingException;
import com.smartinventory.security.token.ConfirmationToken;
import com.smartinventory.security.token.ConfirmationTokenService;
import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final String FRONTEND_BASE_URL = "localhost:3000";

    private final AppUserService userService;
    private final ConfirmationTokenService tokenService;
    private final EmailSenderService emailSender;

    /*
     * Takes in RegistrationDTO object and converts it to new User
     * Registers the new user, and sends out a confirmation email
     * Return token string for new user
     */
    public String register(RegistrationDTO request) {

        String reqEmail = request.getEmail();
        String reqUsername = request.getUsername();

        // Register user and get token
        String token = userService.registerUser(
                new AppUser(reqEmail, reqUsername, request.getPassword()));

        // Form email body
        String confirmationLink = FRONTEND_BASE_URL + "/registration/confirm?token=" + token;
        String emailBody = String.format("Hi, %s!%n%n" +
                "Confirm your email: %s\n\n" +
                "Link will expire in 15 minutes.%n", reqUsername, confirmationLink);

        // Send email
        // emailSender.send(reqEmail, emailBody, "SmartInventory: Confirm Your Email");
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
        ZonedDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(ZonedDateTime.now())) {
            throw new InvalidTokenException("Token not found, already expired!");
        }

        tokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    public ResponseEntity<JwtDTO> login(LoginDTO request) {
        
        // Get username and password (and encode) from request DTO
        String username = request.getUsername();
        String encodedPassword = request.getPassword();

        // Package DTO parameters into User object to login in userService
        return userService.loginUser(new AppUser(null, username, encodedPassword));
    }

    /*
     * Takes in an email, and return a token that pairs with the user
     * to confirm request to reset password
     */
    public String forgetUserPassword(ForgetPasswordDTO request) {

        String reqEmail = request.getEmail();

        // Finds the user from the email entered
        AppUser user = userService.getUserByEmail(reqEmail);

        // Get token for resetting password
        String token = userService.forgetUserPassword(user);
        String reqUsername = user.getUsername();

        // Form email body
        // TODO: URL to be changed to frontend URL
        String confirmationLink = "localhost:8080/api/v1/forget-password/reset?token=" + token;
        String emailBody = String.format("Hi, %s!%n%n" +
                "Reset your password: %s\n\n" +
                "Link will expire in 15 minutes.%n" +
                "If you did not request for a password reset" +
                "you may ignore this email&n", reqUsername, confirmationLink);

        // Send email
        emailSender.send(reqEmail, emailBody, "SmartInventory: Reset Password");
        return token;
    }

    /*
     * Takes in token for confirmation, and a new password
     * to update the previous password where user email matches
     * confirmation token's user email
     */
    public String resetPassword(String token, ResetPasswordDTO request) {

        // Retrieve confirmation token from db (if exists)
        ConfirmationToken confirmationToken = tokenService.getToken(token)
                .orElseThrow(() -> new InvalidTokenException("Token not found!"));

        // Check if token has expired
        ZonedDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(ZonedDateTime.now())) {
            throw new InvalidTokenException("Token not found, already expired!");
        }

        String newPassword = request.getNewPassword();
        String confirmNewPassword = request.getConfirmNewPassword();

        // If passwords entered does not matc
        if (!newPassword.equals(confirmNewPassword)) {
            throw new PasswordNotMatchingException();
        }

        // Confirm token
        tokenService.setConfirmedAt(token);

        // Retrieve email to look for user
        String email = confirmationToken.getUser().getEmail();
        userService.updateUserPassword(email, newPassword);
        return "Reset password";
    }

}
