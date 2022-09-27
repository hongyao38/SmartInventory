package com.smartinventory.user;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@Setter
@Getter
public class User {

    @Id @NotNull @Email
    private String email;
    
    @NotNull
    private String username;

    @NotNull @Size(min=5, max=50)
    private String password;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User() {}

    
}
