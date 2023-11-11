package com.server.controllers;

import com.server.utils.Email;
import com.server.validations.UserCredentials;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.server.errors.CustomException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Garante que o CORS esteja habilitado para o frontend
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCredentials credentials) {

        UserCredentials user = new UserCredentials(
                new Email(credentials.getEmail()),
                credentials.getPassword());

        System.out.println(user);
        return ResponseEntity.ok("Este usuário é válido para se cadastrar.");
    }

    // Este método manipulador de exceções captura CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        // Retorna a mensagem de erro personalizada
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
