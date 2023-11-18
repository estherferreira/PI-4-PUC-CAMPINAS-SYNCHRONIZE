package com.synback.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
public class WebSocketControllerBackend {

    @MessageMapping("/message")
    @SendTo("/topic/reply")
    public String processMessageFromClient(String message) throws Exception {
        return "Resposta do servidor: " + message;
    }
}
