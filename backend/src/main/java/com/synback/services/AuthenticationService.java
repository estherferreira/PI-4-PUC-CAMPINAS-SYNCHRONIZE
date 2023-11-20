package com.synback.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.synback.models.AuthenticationUser;
import com.synback.repositories.AuthenticationUserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Autowired
    private AuthenticationUserRepository userRepository;

    // usar na rota de registro
    public void register(AuthenticationUser user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Este e-mail já está registrado.");
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public String login(String email, String password) {
        AuthenticationUser userInDb = userRepository.findByEmail(email);
        if (userInDb.getEmail() == null) {
            throw new RuntimeException("E-mail não encontrado.");
        }

        if (!BCrypt.checkpw(password, userInDb.getPassword())) {
            throw new RuntimeException("Senha incorreta.");
        }

        return generateTokenForUser(userInDb);
    }

    public String generateTokenForUser(AuthenticationUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId()); // Adicionar o ID do usuário como claim
        return createToken(claims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // Objetivo: Extrai o e-mail do usuário

    // Como Funciona: Utiliza a biblioteca JWT para decodificar o token,
    // verificando-o com a chave secreta (secretKey). Após a decodificação, extrai o
    // assunto (subject) do corpo do token, que neste caso é o e-mail do usuário.
    public String extractEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Extrai o ID do usuário
    public int extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.get("userId", Integer.class);
    }

    // Objetivo: Obtém a data de expiração do token JWT.

    // Como Funciona: Assim como no método anterior, decodifica o token JWT e extrai
    // a data de expiração do seu corpo.
    public Date extractExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    // Objetivo: Verifica se o token JWT expirou.

    // Como Funciona: Usa extractExpirationDateFromToken para obter a data de
    // expiração do token e compara-a com a data e hora atual. Se a data de
    // expiração for anterior à data atual, significa que o token expirou.
    public Boolean isTokenExpired(String token) {
        final Date expiration = extractExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Objetivo: Valida o token JWT para um determinado usuário.

    // Como Funciona: Extrai o nome de usuário (e-mail) do token usando
    // extractUsernameFromToken.

    // Compara o nome de usuário extraído do token com o e-mail do objeto user.
    // Verifica se o token não expirou usando isTokenExpired.

    // Retorna true se o nome de usuário corresponder e o token ainda for válido
    // (não expirado); caso contrário, retorna false.
    public Boolean validateToken(String token, AuthenticationUser user) {
        final String username = extractEmailFromToken(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }
}
