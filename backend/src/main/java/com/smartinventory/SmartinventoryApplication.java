package com.smartinventory;

import org.springframework.context.ApplicationContext;

import com.smartinventory.auth.AuthService;
import com.smartinventory.auth.dto.ForgetPasswordDTO;
import com.smartinventory.auth.dto.RegistrationDTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartinventoryApplication {
	
	public static void main(String[] args) {
		ApplicationContext ctx =  SpringApplication.run(SmartinventoryApplication.class, args);
		AuthService authService = ctx.getBean(AuthService.class);

		// Testing email (CHANGE IF NEEDED)
		String email = "me40@live.com.sg";

		// Register User
		RegistrationDTO regDTO = new RegistrationDTO(email, "xiaodidi", "12345");
		String confirmationToken = authService.register(regDTO);

		// Confirm email
		authService.confirmToken(confirmationToken);
		System.out.println("User email confirmed, ready for testing");

		// Forget password
		authService.forgetUserPassword(new ForgetPasswordDTO(email));
		System.out.println("Forget password email sent");
	}

}
