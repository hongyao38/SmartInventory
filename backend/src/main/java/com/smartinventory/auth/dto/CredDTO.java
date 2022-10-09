package com.smartinventory.auth.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class CredDTO {
    
    @NotNull
    private String username;

    @NotNull
    private String password;
    
}
