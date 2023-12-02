package com.synback.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.synback.models.AuthenticationUser;
import com.synback.models.UserDiagnosis;
import com.synback.models.UserProfile;
import com.synback.models.UserProfileDTO;
import com.synback.repositories.AuthenticationRepository;
import com.synback.repositories.UserProfileRepository;
import com.synback.services.DiagnosticsService;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "*") // Permitir CORS para o frontend
public class DiagnosticController {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private DiagnosticsService diagnosticsService;

    @Autowired
    private UserProfileRepository userRepository;

    @PostMapping("/diagnosis")
    public ResponseEntity<?> newDiagnosis(@RequestBody UserDiagnosis data) {
        String symptoms = data.getSymptoms();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        AuthenticationUser credentials = authenticationRepository.findByEmail(email);
        String userId = credentials.getUserId();

        UserProfile user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        String prompt = diagnosticsService.buildPrompt(user, symptoms);
        System.out.println(prompt);
        String openAiResponse = diagnosticsService.callOpenAiService(prompt);
        System.out.println(openAiResponse);

        UserDiagnosis diagnosis = new UserDiagnosis(userId,
                diagnosticsService.parseOpenAiResponse(openAiResponse),
                symptoms, user.getId(), new Date());

        System.out.println(diagnosticsService.parseOpenAiResponse(openAiResponse));
        System.out.println(diagnosis);

        UserProfile userInfo = userRepository.findByUserId(userId);

        UserProfileDTO userProfileDTO = null;
        if (userInfo != null) {
            userProfileDTO = new UserProfileDTO();
            userProfileDTO.setUserId(userInfo.getId());
            userProfileDTO.setName(userInfo.getName());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("diagnoses", diagnosis);
        response.put("userInfo", userProfileDTO);
        System.out.println(response);

        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}