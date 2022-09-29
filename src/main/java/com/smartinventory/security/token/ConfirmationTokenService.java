package com.smartinventory.security.token;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    
    private final ConfirmationTokenRepository tokenRepository;

    public Optional<ConfirmationToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void saveConfirmationToken(ConfirmationToken token) {
        tokenRepository.save(token);
    }

    public int setConfirmedAt(String token) {
        return tokenRepository.updateConfirmedAt(token, ZonedDateTime.now());
    }
}
