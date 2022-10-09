package com.smartinventory.appuser;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartinventory.auth.dto.JwtDTO;
import com.smartinventory.exceptions.user.InvalidCredentialsException;
import com.smartinventory.exceptions.user.UserEmailNotFoundException;
import com.smartinventory.exceptions.user.UserEmailTakenException;
import com.smartinventory.exceptions.user.UserIdNotFoundException;
import com.smartinventory.exceptions.user.UsernameTakenException;
import com.smartinventory.security.config.JwtUtil;
import com.smartinventory.security.token.ConfirmationToken;
import com.smartinventory.security.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository userRepository;
    private final ConfirmationTokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<AppUser> listAppUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserIdNotFoundException(id));
    }

    public AppUser getUserByEmail(String email) throws UserEmailNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserEmailNotFoundException(email));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Username: %s not found", username)));
    }

    /*
     * Takes in email, finds user by email and enables (verify) user
     */
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    /*
     * Takes in new user request, add user to DB if no user found
     * Confirmation email will be sent out to user
     * If existing user found,
     * throws UserEmailTakenException OR UsernameTakenException
     */
    public String registerUser(AppUser user) {

        String email = user.getEmail();
        String username = user.getUsername();

        // If email is taken, throw exception
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new UserEmailTakenException();
        }
        // If username is taken, throw exception
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameTakenException();
        }

        // Encrypt and set password
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Add user to database
        userRepository.save(user);

        // Create a confirmationToken object to match to user
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        // Save token to database
        tokenService.saveConfirmationToken(confirmationToken);
        return confirmationToken.getToken();
    }

    /*
     * Takes in AppUser object and returns a ResponseEntity with JWT in body
     */
    public ResponseEntity<JwtDTO> loginUser(AppUser user) {

        String username = user.getUsername();
        String password = user.getPassword();

        // If username does not exist
        Optional<AppUser> userRecord = userRepository.findByUsername(username);
        if (userRecord.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        // If password is not matching
        if (!bCryptPasswordEncoder.matches(password, userRecord.get().getPassword())) {
            throw new InvalidCredentialsException();
        }

        // Create JWT token
        UserDetails userDetails = loadUserByUsername(username);
        String accessToken = JwtUtil.createJWT(userDetails);

        return new ResponseEntity<>(new JwtDTO(accessToken), HttpStatus.OK);
    }

    /*
     * Finds user by email, and updates password
     * For reseting of password
     */
    public int updateUserPassword(String email, String password) {
        password = bCryptPasswordEncoder.encode(password);
        return userRepository.resetPassword(email, password);
    }

    /*
     * Takes in user object, and creates a confirmation token
     * linked to the user
     * Token will be used to confirm reset password
     */
    public String forgetUserPassword(AppUser user) {

        // Creates a confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        // Saves confirmation token to database
        tokenService.saveConfirmationToken(confirmationToken);
        return confirmationToken.getToken();
    }


    /*
     * Check if a username already exists
     * Used for real-time validation for front-end form
     */
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
