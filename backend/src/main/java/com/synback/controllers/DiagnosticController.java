package com.synback.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import jakarta.validation.Valid;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import com.synback.services.DiagnosticsService;
// import com.synback.models.UserDiagnosis;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permitir CORS para o frontend
public class DiagnosticController {

    // @Autowired
    // private DiagnosticsService diagnosticsService;

    // @PostMapping("/diagnosis")
    // public ResponseEntity<UserDiagnosis> diagnoseUser(@RequestHeader("Authorization")
    // String token,
    // @RequestBody String symptoms) {
    // UserDiagnosis diagnosis = diagnosticsService.diagnosis(token, symptoms);
    // return new ResponseEntity<>(diagnosis, HttpStatus.OK);
    // }

    // @PostMapping("/diagnosis")
    // public ResponseEntity<Diagnosis> diagnoseUser(@RequestParam String userId, @RequestBody String symptoms) {
    //     Diagnosis diagnosis = diagnosticsService.diagnosis(userId, symptoms);
    //     return new ResponseEntity<>(diagnosis, HttpStatus.OK);
    // }
}
