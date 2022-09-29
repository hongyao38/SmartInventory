package com.smartinventory.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public String register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @GetMapping(path = "registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authService.confirmToken(token);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forget-password")
    public String forgetUserPassword(@RequestParam String email){
        return authService.forgetUserPassword(email);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/forget-password/reset")
    public String resetPassword(@RequestParam("token") String token, @RequestBody String password) {
        return authService.resetPassword(token, password);
    }

}
