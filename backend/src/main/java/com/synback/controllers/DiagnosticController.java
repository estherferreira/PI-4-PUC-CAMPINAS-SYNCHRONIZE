package com.synback.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.synback.models.AuthenticationUser;
import com.synback.models.UserDiagnosis;
import com.synback.models.UserDiagnosis.ReportItem;
import com.synback.models.UserProfile;
import com.synback.models.UserProfileDTO;
import com.synback.repositories.AuthenticationRepository;
import com.synback.repositories.DiagnosisRepository;
import com.synback.repositories.UserProfileRepository;
import com.synback.services.DiagnosticsService;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" }) // Permitir CORS para o frontend
public class DiagnosticController {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private DiagnosticsService diagnosticsService;

    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @PostMapping("/diagnosis")
    public ResponseEntity<?> newDiagnosis(@RequestBody UserDiagnosis data) {
        String symptoms = data.getSymptoms();

        // Obtem o id do usuário
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        AuthenticationUser credentials = authenticationRepository.findByEmail(email);
        String userId = credentials.getUserId();

        UserProfile user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        // Envia sintomas e dados do perfil do usuário para o OpenAI
        String prompt = diagnosticsService.buildPrompt(user, symptoms);
        System.out.println(prompt);

        String openAiResponse = diagnosticsService.callOpenAiService(prompt);
        System.out.println("OpenAI response: " + "\n" + openAiResponse);

        List<ReportItem> report = parseOpenAiResponse(openAiResponse);
        System.out.println("Report: " + "\n" + report);

        // Salva o diagnóstico no banco de dados
        UserDiagnosis diagnosis = new UserDiagnosis(userId, report, symptoms, user.getId(), new Date());
        System.out.println("Diagnóstico: " + "\n" + diagnosis);
        diagnosisRepository.insert(diagnosis);

        return ResponseEntity.ok("Diagnóstico realizado e enviado para o banco de dados com sucesso");
    }

    // Deixa a resposta da OpenAI no formato da classe UserDiagnostics - ReportItem
    private List<ReportItem> parseOpenAiResponse(String response) {
        List<UserDiagnosis.ReportItem> reportItems = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "Problema: (.*?)\\nChance de ocorrer: (.*?)\\nDescrição: (.*?)(?=\\n\\nProblema:|\\Z)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            String problem = matcher.group(1).trim();
            String chanceOfOccurrence = matcher.group(2).trim();
            String description = matcher.group(3).trim();
            reportItems.add(new UserDiagnosis.ReportItem(problem, chanceOfOccurrence, description));
        }
        return reportItems;
    }

    @GetMapping("/diagnosis")
    public ResponseEntity<?> getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        AuthenticationUser credentials = authenticationRepository.findByEmail(email);
        String userId = credentials.getUserId();

        List<UserDiagnosis> diagnoses = diagnosisRepository.findByUserId(userId);

        UserProfile userInfo = userRepository.findByUserId(userId);

        UserProfileDTO userProfileDTO = null;
        if (userInfo != null) {
            userProfileDTO = new UserProfileDTO();
            userProfileDTO.setUserId(userInfo.getId());
            userProfileDTO.setName(userInfo.getName());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("diagnoses", diagnoses);
        response.put("userInfo", userProfileDTO);

        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}