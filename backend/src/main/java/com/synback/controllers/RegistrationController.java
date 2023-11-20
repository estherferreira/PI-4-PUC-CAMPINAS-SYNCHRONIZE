package com.synback.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class RegistrationController {

    @PostMapping("/registration")
    public ResponseEntity<?> validateCredentials(@RequestBody Object data) {
        System.out.println(data);
        return ResponseEntity.ok("Credenciais validadas e salvas com sucesso.");
    }
}
