package com.synback.services;

import com.synback.models.User;
import com.synback.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Adicionar um usuário ao banco de dados
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // Obter informações do usuário por ID
    // Optional<User> ao inves de User pois pode ser que o documento não exista no BD
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    // Remover um usuário do banco de dados
    public void removeUser(String userId) {
        userRepository.deleteById(userId);
    }

    // Atualizar informações de um usuário
    public User updateUser(User updatedUser) {
        return userRepository.save(updatedUser);
    }
}
