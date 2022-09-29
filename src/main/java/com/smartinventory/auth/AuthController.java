package com.smartinventory.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value="/forget-password", method=RequestMethod.POST)
    public void forgotUserPassword(@RequestBody AuthRequest request){
        authService.forgetUserPassword(request);
    }

}
