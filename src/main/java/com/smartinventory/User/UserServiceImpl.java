package com.smartinventory.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smartinventory.security.ConfirmationToken;
import com.smartinventory.security.ConfirmationTokenRepository;
import com.smartinventory.security.EmailSenderService;

import org.springframework.mail.SimpleMailMessage;


@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findById(email).orElse(null);
    }

    @Override
    public String getEmail(String username) {
        return userRepository.findEmailByUsername(username);
    }

    @Override
    public User updateUser(String email, User newUser) {
        return userRepository.findById(email).map(user -> {user.setPassword(newUser.getPassword());
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteById(email);
    }

    /*
     * Create new exceptions to throw for cases 
     * where users are not added successfully
     * Update UserServiceImpl accordingly
     */
    public User addUser(User user) {
        if (!userRepository.findByUsername(user.getUsername()).isEmpty()) {
            return null;
        }
        return userRepository.save(user);
    }

    @Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
    
    @Autowired
	private EmailSenderService emailSenderService;


    /**
	 * Display the forgot password page and form
	 */
	@RequestMapping(value="/forgot-password", method=RequestMethod.GET)
	public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
	}
    
    public ModelAndView forgetUserPassword(ModelAndView modelAndView, User user ){
        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());

        if(existingUser != null){
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("geraldng1999@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
			+"smart-inventory.cuqci4fvc8xa.ap-northeast-1.rds.amazonaws.com/confirm-reset?token="+confirmationToken.getConfirmationToken());
            
            //Send the email;
            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("message","Request to reset password recieved");
            modelAndView.setViewName("successForgotPassword");

        }else{
            modelAndView.addObject("message", "This email address does not exist!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    
}
