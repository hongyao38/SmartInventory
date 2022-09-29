package com.smartinventory.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartinventory.exceptions.user.UserEmailNotFoundException;
import com.smartinventory.exceptions.user.UserEmailTakenException;
import com.smartinventory.exceptions.user.UserIdNotFoundException;
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

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserIdNotFoundException("ID: " + id));
    }

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
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
        );

        // Save token to database
        tokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken.getToken();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserEmailNotFoundException(username));
    }

    /*
     * Finds user by email
     * If email does not exist,
     * throws UserEmailNotFoundException
     */
    public User getUserByEmail(String email) throws UserEmailNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new UserEmailNotFoundException(email)
        );
    }

    public String getEmail(String username) {
        return userRepository.findEmailByUsername(username);
    }

    /*
     * Finds user by email, and updates password
     * If email does not exist,
     * throws UserEmailNotFoundException
     */
    public int updateUserPassword(String email, String password) throws UserEmailNotFoundException {
        password = bCryptPasswordEncoder.encode(password);
        return userRepository.resetPassword(email, password);
    }

    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }
    
    /*
     * Takes in user object, looks for user via user email
     * Sends reset password email if user found
     * If user not found,
     * throws UserEmailNotFoundException
     */
    public String forgetUserPassword(User user) throws UserEmailNotFoundException {

        // Creates a confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
        );

        // Saves confirmation token to database
        tokenService.saveConfirmationToken(confirmationToken);
        return confirmationToken.getToken();
    }
}
