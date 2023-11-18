package com.synback.controllers;

// import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synback.services.DiagnosticsService;
import com.synback.models.Diagnosis;

@RestController
@RequestMapping("/diagnostic")
@CrossOrigin(origins = "http://localhost:3000") // Permitir CORS para o frontend
public class DiagnosticController {

    private final DiagnosticsService diagnosticsService;

    // Injetar DiagnosticsService usando construtor
    public DiagnosticController(DiagnosticsService diagnosticsService) {
        this.diagnosticsService = diagnosticsService;
    }

    // Endpoint para receber sintomas e retornar diagnóstico
    @PostMapping
    public ResponseEntity<?> getDiagnostic(@RequestBody String symptoms, @RequestParam String userId) {
        try {
            Diagnosis diagnosis = diagnosticsService.diagnose(userId, symptoms);
            return ResponseEntity.ok(diagnosis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
