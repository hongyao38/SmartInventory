package com.smartinventory.user;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartinventory.exceptions.user.InvalidPasswordException;
import com.smartinventory.exceptions.user.UserEmailNotFoundException;
import com.smartinventory.exceptions.user.UserEmailTakenException;
import com.smartinventory.exceptions.user.UserIdNotFoundException;
import com.smartinventory.exceptions.user.UsernameNotFoundException;
import com.smartinventory.exceptions.user.UsernameTakenException;
import com.smartinventory.security.token.ConfirmationToken;
import com.smartinventory.security.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    // TODO: Propagate the exception to Controller
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserIdNotFoundException(id));
    }

    public User getUserByEmail(String email) throws UserEmailNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new UserEmailNotFoundException(email)
        );
    }

    // POSSIBLY REDUNDANT
    // public String getEmail(String username) {
    //     return userRepository.findEmailByUsername(username);
    // }

    // public void deleteUser(String email) {
    //     userRepository.deleteByEmail(email);
    // }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserEmailNotFoundException(username));
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
    public String registerUser(User user) {

        String email = user.getEmail();
        String username = user.getUsername();

        // If email is taken, throw exception
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new UserEmailTakenException(email);
        }
        // If username is taken, throw exception
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameTakenException(username);
        }

        // Encrypt and set password
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Add user to database
        userRepository.save(user);

        // Create a confirmationToken object to match to user
        ConfirmationToken confirmationToken = new ConfirmationToken(
            ZonedDateTime.now(),
            ZonedDateTime.now().plusMinutes(15),
            user
        );

        // Save token to database
        tokenService.saveConfirmationToken(confirmationToken);
        return confirmationToken.getToken();
    }

    /*
     * 
     */
    public ResponseEntity<String> loginUser(User user) {

        String username = user.getUsername();
        String password = user.getPassword();

        // If username does not exist, throw UsernameNotFoundException
        Optional<User> userRecord = userRepository.findByUsername(username);
        if (userRecord.isEmpty()) {
            System.out.println("UserService: login reached here");
            throw new UsernameNotFoundException();
        }

        // If password is not matching, throw BadCredentialsException
        if (!bCryptPasswordEncoder.matches(password, userRecord.get().getPassword())) {
            throw new InvalidPasswordException();
        }
        // Success
        return new ResponseEntity<>(String.format("%s: login success", username), HttpStatus.OK);
    }

    /*
     * Finds user by email, and updates password
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
    public String forgetUserPassword(User user) {

        // Creates a confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
            ZonedDateTime.now(),
            ZonedDateTime.now().plusMinutes(15),
            user
        );

        // Saves confirmation token to database
        tokenService.saveConfirmationToken(confirmationToken);
        return confirmationToken.getToken();
    }
}
