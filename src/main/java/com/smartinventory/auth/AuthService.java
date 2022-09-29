package com.smartinventory.auth;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String register(AuthRequest request) {
        return userService.registerUser(
            new User(request.getEmail(), request.getUsername(), request.getPassword())
        );
    }

    @Transactional
    public String confirmToken(String token) {

        // Retrieve confirmation token from db (if exists)
        ConfirmationToken confirmationToken = tokenService.getToken(token)
            .orElseThrow(() -> new InvalidTokenException(token));

        // Check if token has already been used to verify email
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyVerifiedException();
        }

        // Check if token has expired
        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token has already expired!");
        }

        tokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    public User forgetUserPassword(AuthRequest request) {
        return userService.forgetUserPassword(
            new User(request.getEmail(), request.getUsername(), request.getPassword()));
    }
    
}
