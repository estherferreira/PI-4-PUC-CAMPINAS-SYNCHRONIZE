package com.server.configurations;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import com.server.errors.CustomException;

public class SocketServer {
    public static void main(String[] args) {
        short port = 7000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String credentials = in.readLine();
                try {
                    validateCredentials(credentials);
                    out.println("valido");
                } catch (Exception e) {
                    out.println(e.getMessage());
                }
            } catch (IOException e) {
                System.out.println("Erro ao interagir com o cliente: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o socket do cliente: " + e.getMessage());
                }
            }
        }

        private void validateCredentials(String credentials) throws CustomException {
            // Espera-se que as credenciais venham no formato "email:senha"
            String[] parts = credentials.split(":");
            if (parts.length != 2) {
                throw new CustomException("Formato de credenciais inválido.", 1002);
            }

            String email = parts[0];
            String password = parts[1];

            if (!isValidEmail(email)) {
                throw new CustomException("Endereço de e-mail inválido.", 1007);
            }

            if (!isPasswordStrong(password)) {
                throw new CustomException(
                        "Para atender aos critérios de segurança, sua senha de conter pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.",
                        1001);
            }
        }

        private boolean isValidEmail(String email) {
            // Implementação da validação de e-mail
            return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
        }

        private boolean isPasswordStrong(String password) {
            // Implementação da validação da força da senha
            return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        }
    }
}