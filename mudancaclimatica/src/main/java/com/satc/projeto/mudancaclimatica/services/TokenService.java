package com.satc.projeto.mudancaclimatica.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.expiration-minutes}")
    private Long expirationMinutes;

    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC512(this.secret);
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(this.expirationMinutes, ChronoUnit.MINUTES);
        try {
            String token = JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(username)
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
