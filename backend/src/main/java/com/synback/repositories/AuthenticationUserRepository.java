package com.synback.repositories;

import com.synback.models.AuthenticationUser;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationUserRepository extends GenericRepository<AuthenticationUser, String> {
    AuthenticationUser findByEmail(String email);
}