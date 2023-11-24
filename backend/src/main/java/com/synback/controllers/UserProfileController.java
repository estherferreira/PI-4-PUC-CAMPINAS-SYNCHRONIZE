package com.synback.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.synback.models.UserDiagnosis;
import com.synback.repositories.DiagnosisRepository;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class UserProfileController {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<List<UserDiagnosis>> getDiagnosesForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        System.out.println("Email: " + email);

        List<UserDiagnosis> diagnoses = diagnosisRepository.findByEmailIgnoreCase(email);
        if (!diagnoses.isEmpty()) {
            return ResponseEntity.ok(diagnoses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
