package com.mateuszczyz.Todo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mateuszczyz.Todo.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.System.currentTimeMillis;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtConfig jwtConfig;

    public String verifyToken(String token) {
        JWTVerifier verifier = JWT.require(jwtConfig.getAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public String generateToken(String subject) {
        Date tokenExpiration = new Date(currentTimeMillis() + jwtConfig.getTokenExpiration());
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(tokenExpiration)
                .sign(jwtConfig.getAlgorithm());
    }
}
