package com.server.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.server.errors.CustomException;
import com.server.utils.Email;
import com.server.validations.UserCredentials;

@Controller
public class WebSocketUserController {

    @MessageMapping("/register")
    @SendTo("/topic/userResponses")
    public String register(UserCredentials credentials) {
        try {
            UserCredentials user = new UserCredentials(new Email(credentials.getEmail()), credentials.getPassword());
            System.out.println(user);
            return "Este usuário é válido para se cadastrar.";
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            // Aqui você precisa assegurar que a mensagem de erro será enviada ao cliente
            return "Erro de registro: " + e.getMessage();
        }
    }
}
