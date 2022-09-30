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
    
    // REGISTRATION
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public String register(@RequestBody RegistrationDTO request) {
        return authService.register(request);
    }

    // CONFIRM REGISTRATION
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authService.confirmToken(token);
    }

    // LOGIN
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/login")
    public String login(@RequestBody LoginDTO request) {
        return authService.login(request);
    }

    // FORGET PASSWORD REQUEST
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forget-password")
    public String forgetUserPassword(@RequestBody ForgetPasswordDTO request){
        return authService.forgetUserPassword(request);
    }

    // CONFIRM AND RESET PASSWORD
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/forget-password/reset")
    public String resetPassword(@RequestParam("token") String token, @RequestBody String password) {
        return authService.resetPassword(token, password);
    }

}
