package com.synback.repositories;

//Este código define um repositório para gerenciar a entidade
//AuthenticationUser, permitindo operações de banco de
//dados, como buscar um usuário pelo email.

//É usado para simplificar o acesso e a manipulação dos dados de usuários autenticados no bd

import com.synback.models.AuthenticationUser;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationUserRepository extends
GenericRepository<AuthenticationUser, String> {
AuthenticationUser findByEmail(String email);
}