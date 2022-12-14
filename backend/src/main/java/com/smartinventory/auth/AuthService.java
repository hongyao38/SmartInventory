package com.smartinventory.auth;

import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserService;
import com.smartinventory.auth.dto.CredDTO;
import com.smartinventory.auth.dto.ForgetPasswordDTO;
import com.smartinventory.auth.dto.JwtDTO;
import com.smartinventory.auth.dto.RegistrationDTO;
import com.smartinventory.auth.dto.ResetPasswordDTO;
import com.smartinventory.email.EmailSenderService;
import com.smartinventory.exceptions.token.EmailAlreadyVerifiedException;
import com.smartinventory.exceptions.token.InvalidTokenException;
import com.smartinventory.exceptions.user.PasswordNotMatchingException;
import com.smartinventory.security.token.ConfirmationToken;
import com.smartinventory.security.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final String FRONTEND_BASE_URL = "http://localhost:3000";
    private final String CONFIRMATION_EMAIL = "src\\main\\java\\com\\smartinventory\\email\\html\\confirmationemail.html";
    private final String RESET_PASSWORD_EMAIL = "src\\main\\java\\com\\smartinventory\\email\\html\\forgetpassword.html";

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
        String emailBody = emailSender.readHTML(CONFIRMATION_EMAIL);
        emailBody = emailBody.replace("INSERT CONFIRMATION LINK", confirmationLink);

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
        ZonedDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(ZonedDateTime.now())) {
            throw new InvalidTokenException("Token not found, already expired!");
        }

        tokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    public ResponseEntity<JwtDTO> login(CredDTO request) {
        
        // Get username and password (and encode) from request DTO
        String usernameOrEmail = request.getUsername();
        String encodedPassword = request.getPassword();

        // Package DTO parameters into User object to login in userService
        return userService.loginUser(usernameOrEmail, encodedPassword);
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

        // Form email body
        String confirmationLink = FRONTEND_BASE_URL + "/forget-password/reset?token=" + token;
        String emailBody = emailSender.readHTML(RESET_PASSWORD_EMAIL);
        emailBody = emailBody.replace("INSERT CONFIRMATION LINK", confirmationLink);

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
