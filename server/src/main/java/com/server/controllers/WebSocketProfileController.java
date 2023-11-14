package com.server.controllers;

import com.server.errors.CustomException;
import com.server.validations.PersonalInfo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketProfileController {

    @MessageMapping({"/registration", "my-data"})
    @SendTo("/topic/profileResponses")
    public String registration(PersonalInfo personalInfo) {
        try {
            new PersonalInfo(
                    personalInfo.getName(),
                    personalInfo.getWeight(),
                    personalInfo.getHeight(),
                    personalInfo.getExerciseTime(),
                    personalInfo.getDateOfBirth());
            return "Os dados são válidos para cadastro.";
        } catch (CustomException e) {
            return "Erro no cadastro: " + e.getMessage();
        }
    }
}