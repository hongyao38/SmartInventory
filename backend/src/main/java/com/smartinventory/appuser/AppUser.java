package com.smartinventory.appuser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartinventory.inventory.consumption.Consumption;

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

    @NotNull
    private String authorities;

    private Boolean isEnabled = false; // Whether the user is verified

    @JsonIgnore
    @OneToMany(mappedBy = "user", 
                orphanRemoval = true, 
                cascade = CascadeType.ALL)
    private List<Consumption> consumptions;

    public AppUser(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        authorities = "ROLE_USER"; // FOR NOW: Default is USER
    }

    public AppUser(String email, String username, String password, String authorities) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
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
