package com.synback.controllers;

import org.springframework.web.bind.annotation.*;

import com.synback.models.Register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
public class RegisterController {

    @PostMapping("/register")
    public String validateCredentials(@RequestBody Register credentials) {

        String email = credentials.getEmail();
        String password = credentials.getPassword();

        // Envia as credenciais e recebe a resposta
        String response = sendCredentialsToSocketServer(email, password);
        System.out.println("Resposta: " + response);
        return response;

    }

    public String sendCredentialsToSocketServer(String email, String password) {
        try (Socket socket = new Socket("localhost", 7000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(email + ":" + password);
            return in.readLine();

        } catch (IOException e) {
            return "Erro ao se comunicar com o servidor de socket";
        }
    }
}