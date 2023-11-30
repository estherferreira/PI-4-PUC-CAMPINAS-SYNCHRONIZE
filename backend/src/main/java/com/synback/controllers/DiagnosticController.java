package com.synback.controllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synback.models.UserDiagnosis;
import com.synback.models.UserProfile;
import com.synback.repositories.DiagnosisRepository;
import com.synback.repositories.UserProfileRepository;
import com.synback.services.DiagnosticsService;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "*") // Permitir CORS para o frontend
public class DiagnosticController {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private DiagnosticsService diagnosticsService;

    @Autowired
    private UserProfileRepository userRepository;

    @PostMapping("/diagnosis")
    public ResponseEntity<?> newDiagnosis(@RequestBody UserDiagnosis data) {
        String symptoms = data.getSymptoms();
        String email = data.getEmail();

        UserProfile user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        String prompt = diagnosticsService.buildPrompt(user, symptoms);
        System.out.println(prompt);
        String openAiResponse = diagnosticsService.callOpenAiService(prompt);
        System.out.println(openAiResponse);

        UserDiagnosis diagnosis = new UserDiagnosis(generateUniqueId(),
                diagnosticsService.parseOpenAiResponse(openAiResponse),
                symptoms, user.getName(), email, user.getId());

        System.out.println(diagnosticsService.parseOpenAiResponse(openAiResponse));
        System.out.println(diagnosis);

        return ResponseEntity.ok("OK");

    }

    private static String generateUniqueId() {
        Random random = new Random();
        // Gera um número aleatório com 10 dígitos
        int number = random.nextInt(1000000000, 2000000000);
        return "SYN-" + String.format("%010d", number);
    }

}