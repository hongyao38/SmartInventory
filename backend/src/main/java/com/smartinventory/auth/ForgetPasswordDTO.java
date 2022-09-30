package com.smartinventory.auth;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordDTO {
    
    @NotNull
    private String email;
}
