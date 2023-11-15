package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import com.server.errors.CustomException;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

		// try {
		// 	CustomException erro = new CustomException("Error", 1);
		// 	System.out.println(erro);
		// } catch (Exception e) {
		// 	System.out.println("Não entrou no try");
		// } catch (ExceptionInInitializerError e) {
		// 	System.out.println("A classe não funciona");
		// }
	}

}