package br.com.fiap.flashink_api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.flashink_api.model.Token;
import br.com.fiap.flashink_api.model.User;
import br.com.fiap.flashink_api.model.UserRole;

@Service
public class TokenService {

    private Instant expiresAt = LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.ofHours(-3));
    private Algorithm algorithm = Algorithm.HMAC256("secret-muito-secreto-que-ninguem-pode-saber");
    
    public Token createToken(User user) {


        var jwt = JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("email", user.getEmail())
                    .withClaim("role", user.getRole().toString())
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);

        return new Token(jwt, "Bearer", user.getEmail());
    }

    public User getUserFromToken(String jwt) {
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);
        return User.builder()
                .id(Long.valueOf(jwtVerified.getSubject()))
                .email(jwtVerified.getClaim("email").toString())
                .role(UserRole.USER)
                .build();
    }
}
