package com.smartinventory.user;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.listUsers();
    }

    @PostMapping("/users")
    public User addUser(@Valid User user) {
        return userService.addUser(user);
    }

    //new stuff 
    // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    
    @RequestMapping(value="/forget-password", method=RequestMethod.POST)
    public ModelAndView forgotUserPassword(ModelAndView modeAndView, User user){
        return userService.forgetUserPassword(modeAndView, user);
    }
}
