package com.server.controllers;

import com.server.models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketControllerServer {

    @MessageMapping("/message")
    @SendTo("/topic/reply")
    public Message processMessageFromClient(Message message) throws Exception {
        return new Message("Resposta do Servidor: " + message.getContent());
    }
}
