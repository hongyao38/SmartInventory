package com.smartinventory;

import com.smartinventory.user.*;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SmartinventoryApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SmartinventoryApplication.class, args);

		UserController controller = ctx.getBean(UserController.class);

		List<User> users = controller.getUsers();
		for (User u : users) {
			System.out.println(u.getUsername());
		}
	}

}
