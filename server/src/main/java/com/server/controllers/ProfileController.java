package com.server.controllers;

import com.server.validations.PersonalInfo;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:3000") // Garante que o CORS esteja habilitado para o frontend
public class ProfileController {

    @PostMapping("/registration")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody PersonalInfo personalInfo) {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/my-data")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(true);
    }
}
