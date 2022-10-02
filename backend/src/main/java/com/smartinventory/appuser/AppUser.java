package com.smartinventory.appuser;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_seqeunce")
    private Long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String username;

    @NotNull
    @Size(min = 5, max = 50)
    private String password;

    // TODO: Implement the roles for the users (for authorization)
    @NotNull
    private String authorities;

    private Boolean isEnabled = false; // Whether the user is verified

    public AppUser(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        authorities = "ROLE_ADMIN"; // FOR NOW: Default is USER
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authorities));
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    // NOTE: NOT PLANNING TO BE IMPLEMENTED
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
