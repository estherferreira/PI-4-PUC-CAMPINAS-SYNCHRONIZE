package com.server.controllers;

import com.server.validations.PersonalInfo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:3000") // Garante que o CORS esteja habilitado para o frontend
public class ProfileController {

    @PostMapping({ "/registration", "/my-data" })
    public ResponseEntity<?> registration(@Valid @RequestBody PersonalInfo personalInfo) {

        PersonalInfo persona = new PersonalInfo(
                personalInfo.getName(),
                personalInfo.getWeight(),
                personalInfo.getHeight(),
                personalInfo.getExerciseTime(),
                personalInfo.getDateOfBirth());

        System.out.println(persona);
        return ResponseEntity.ok("Os dados são válidos para cadastro.");
    }

    // Este método manipulador de exceções captura CustomException
    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errorMessage = ex.getBindingResult().getAllErrors().stream()
                             .map(ObjectError::getDefaultMessage)
                             .findFirst()
                             .orElse("Erro de validação");

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorMessage);
}
}