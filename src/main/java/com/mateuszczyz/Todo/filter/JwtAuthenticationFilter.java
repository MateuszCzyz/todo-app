package com.mateuszczyz.Todo.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mateuszczyz.Todo.config.JwtConfig;
import com.mateuszczyz.Todo.config.ObjectMapperConfig;
import com.mateuszczyz.Todo.exception.ExceptionResponse;
import com.mateuszczyz.Todo.model.User;
import com.mateuszczyz.Todo.service.UserService;
import com.mateuszczyz.Todo.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final JwtConfig jwtConfig;
    private final Clock clock;
    private final ObjectMapper objectMapper;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();
        if(servletPath.equals("/api/auth/login") || servletPath.equals("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            int httpStatus = HttpStatus.FORBIDDEN.value();
            String errorMessage = "Provided token must not be empty";

            ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                    .httpStatus(httpStatus)
                    .messages(List.of(errorMessage))
                    .timestamp(LocalDateTime.now(clock))
                    .build();

            response.setStatus(httpStatus);
            response.setContentType(APPLICATION_JSON_VALUE);

            objectMapper.writeValue(response.getWriter(), exceptionResponse);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = authorizationHeader.substring(jwtConfig.getTokenPrefix().length());
            String jwtSubject = jwtUtils.verifyToken(jwtToken);

            User user = (User) userService.loadUserByUsername(jwtSubject);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword(),
                    user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);

        } catch (JWTVerificationException | AuthenticationException e) {
            int httpStatus = HttpStatus.FORBIDDEN.value();
            String errorMessage = "Provided token is not valid";

            ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                    .httpStatus(httpStatus)
                    .messages(List.of(errorMessage))
                    .timestamp(LocalDateTime.now(clock))
                    .build();

            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(httpStatus);

            objectMapper.writeValue(response.getOutputStream(), exceptionResponse);
            filterChain.doFilter(request, response);
        }
    }
}
