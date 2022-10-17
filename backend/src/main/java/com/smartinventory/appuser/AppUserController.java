package com.smartinventory.appuser;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartinventory.auth.dto.CredDTO;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin
public class AppUserController {

    private AppUserService userService;

    @GetMapping("/users")
    public List<AppUser> getUsers() {
        return userService.listAppUsers();
    }

    @GetMapping("/users/{id}")
    public AppUser getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/users/check-username")
    public boolean usernameExists(@RequestBody CredDTO request) {
        System.out.println("REQUEST TO CHECK RECEIVED: | " + request.getUsername() + " | ");
        return userService.usernameExists(request.getUsername());
    }
}