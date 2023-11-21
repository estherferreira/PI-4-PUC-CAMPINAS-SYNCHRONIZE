package com.synback.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import com.synback.utils.Data;
import com.synback.models.UserProfile;
import com.synback.repositories.UserProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class RegistrationController {

    @Autowired
    private UserProfileRepository userRepository;

    @PostMapping("/registration")
    public ResponseEntity<?> validateUserData(@RequestBody UserProfile data) {

        String name = data.getName();
        Data dateOfBirth = data.getDateOfBirth();
        System.out.println(dateOfBirth);
        int weight = data.getWeight();
        int height = data.getHeight();
        String gender = data.getGender();
        int exerciseTime = data.getExerciseTime();
        String diseaseHistory = data.getDiseaseHistory();

        // Enviar dados para o servidor de socket
        String response = sendUserProfileToSocketServer(dateOfBirth, weight, height, exerciseTime);

        // Ler a resposta do servidor de socket
        System.out.println("Resposta: " + response);

        // Se a resposta for "valido", salva no banco de dados
        if ("sucesso".equals(response)) {
            UserProfile userProfile = new UserProfile(generateUniqueId(), name, dateOfBirth, weight, height, gender,
                    exerciseTime, diseaseHistory);
            userRepository.insert(userProfile);

            System.out.println(userProfile);
            System.out.println("Informações cadastradas no banco de dados.");

            return ResponseEntity.ok("Usuário validado e salvo com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public String sendUserProfileToSocketServer(Data dateOfBirth, int weight, int height, int exerciseTime) {
        try (Socket socket = new Socket("localhost", 7000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("USER:" + dateOfBirth + ":" + weight + ":" + height + ":" + exerciseTime);
            return in.readLine();

        } catch (IOException e) {
            return "Erro ao se comunicar com o servidor de socket";
        }
    }

    private static String generateUniqueId() {
        Random random = new Random();
        // Gera um número aleatório com 10 dígitos
        int number = random.nextInt(1000000000, 2000000000);
        return "SYN-" + String.format("%010d", number);
    }
}
