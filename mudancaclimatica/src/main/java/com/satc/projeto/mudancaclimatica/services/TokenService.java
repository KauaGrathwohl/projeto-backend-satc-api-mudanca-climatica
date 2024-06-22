package com.satc.projeto.mudancaclimatica.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.satc.projeto.mudancaclimatica.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.expiration-minutes}")
    private Long expirationMinutes;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC512(this.secret);
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(this.expirationMinutes, ChronoUnit.MINUTES);
        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("role", user.getRole().name());
            String token = JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(user.getUsername())
                    .withPayload(payload)
                    .withIssuedAt(issuedAt)
                    .withExpiresAt(expiration)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating the JWT token");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(token);
            String username = JWT
                    .require(algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();
            return username;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Error while decoding the JWT token");
        }
    }
}
