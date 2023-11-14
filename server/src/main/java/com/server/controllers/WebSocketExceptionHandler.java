package com.server.controllers;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.server.errors.CustomException;

@Controller
public class WebSocketExceptionHandler {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageExceptionHandler(CustomException.class)
    public void handleCustomException(CustomException e) {
        // Envia a mensagem de erro para "/topic/errors", inscrito no cliente
        messagingTemplate.convertAndSend("/topic/errors", e.getMessage());
    }
}