package com.smartinventory.appuser;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
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
}

// localhost:8080/api/v1/users