package com.mateuszczyz.Todo.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Component
@Configuration
public class JwtConfig {

    @Getter(PRIVATE)
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-prefix}")
    private String tokenPrefix;

    @Value("${jwt.access-token.expiration-time}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration-time}")
    private Long refreshTokenExpiration;

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC384(secretKey);
    }
}
