package com.server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private String email;

    public Email(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Endereço de e-mail inválido.");
        }
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Endereço de e-mail inválido.");
        }
    }

    // Verifica se o email é válido (tem o formato de um email)
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && !email.contains("@.");
    }

    @Override
    public String toString() {
        return email;
    }
}