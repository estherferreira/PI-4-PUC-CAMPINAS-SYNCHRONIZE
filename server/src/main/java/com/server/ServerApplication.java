package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.server.models.User;
import com.server.utils.Email;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	
		User user = new User("001575", new Email("estela@gmail.com"), "Senha123!", "Cliente");
		System.out.println(user);
	
	}

}
