package com.smartinventory;

import com.smartinventory.user.*;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartinventoryApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SmartinventoryApplication.class, args);

		// UserController controller = ctx.getBean(UserController.class);

		// controller.addUser(new User("hello@live.com.sg", "Bobby", "123456"));

		// List<User> users = controller.getUsers();
		// for (User u : users) {
		// 	System.out.println(u.getUsername());
		// }
	}

}
