package com.server.services;

import com.server.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserCommunicationService {

    private final RestTemplate restTemplate;
    private final String backendUrl = "http://endereco-do-backend/api/users";

    public UserCommunicationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User findByEmail(String email) {
        // Aqui, fazemos uma chamada HTTP ao backend para obter um usuário pelo email
        return restTemplate.getForObject(backendUrl + "/search?email=" + email, User.class);
    }

    public void save(User user) {
        // Aqui, fazemos uma chamada HTTP ao backend para salvar um novo usuário
        restTemplate.postForObject(backendUrl, user, User.class);
    }
}