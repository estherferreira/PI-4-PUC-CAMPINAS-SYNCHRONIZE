package com.server.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.server.models.User;

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
    private UserCommunicationService userCommunicationService;

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    
    public void register(User user) {
        if (userCommunicationService.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Este e-mail já está registrado.");
        }
        userCommunicationService.save(user);
    }

    public String login(String email, String password) {
        User userInDb = userCommunicationService.findByEmail(email);
        if (userInDb == null) {
            throw new RuntimeException("E-mail não encontrado.");
        }

        return generateTokenForUser(userInDb);
    }
    
    public String generateTokenForUser(User user) {
        Map<String, Object> claims = new HashMap<>();
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

    public String extractUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Date extractExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, User user) {
        final String username = extractUsernameFromToken(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }
}
