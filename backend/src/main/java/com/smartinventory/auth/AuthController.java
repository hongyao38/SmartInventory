package com.smartinventory.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartinventory.auth.dto.*;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    
    // REGISTRATION
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public String register(@RequestBody RegistrationDTO request) {
        return authService.register(request);
    }

    // CONFIRM REGISTRATION
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authService.confirmToken(token);
    }

    // LOGIN
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/login")
    public ResponseEntity<JwtDTO> login(@RequestBody CredDTO request) {
        return authService.login(request);
    }

    // FORGET PASSWORD REQUEST
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/forget-password")
    public String forgetUserPassword(@RequestBody ForgetPasswordDTO request) {
        return authService.forgetUserPassword(request);
    }

    // CONFIRM AND RESET PASSWORD
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/forget-password/reset")
    public String resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordDTO request) {
        return authService.resetPassword(token, request);
    }

}
