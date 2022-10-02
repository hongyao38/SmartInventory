package com.smartinventory.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationDTO {
    
    @NotNull @Email
    private final String email;

    @NotNull
    private final String username;
    
    @NotNull
    private final String password;

}
