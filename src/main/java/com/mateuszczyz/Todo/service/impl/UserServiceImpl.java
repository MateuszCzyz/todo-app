package com.mateuszczyz.Todo.service.impl;

import com.mateuszczyz.Todo.dto.request.LoginRequestDto;
import com.mateuszczyz.Todo.dto.request.RegisterRequestDto;
import com.mateuszczyz.Todo.dto.request.VerifyTokenRequestDto;
import com.mateuszczyz.Todo.dto.response.LoginResponseDto;
import com.mateuszczyz.Todo.dto.response.RegisterResponseDto;
import com.mateuszczyz.Todo.dto.response.VerifyTokenResponseDto;
import com.mateuszczyz.Todo.exception.TokenVerificationException;
import com.mateuszczyz.Todo.exception.UserAlreadyExistsException;
import com.mateuszczyz.Todo.mapper.UserMapper;
import com.mateuszczyz.Todo.model.User;
import com.mateuszczyz.Todo.repository.UserRepository;
import com.mateuszczyz.Todo.service.UserService;
import com.mateuszczyz.Todo.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationConfiguration authConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final Clock clock;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    String message = format("User with email %s not found", email);
                    return new UsernameNotFoundException(message);
                });
    }

    @Override
    public LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getEmail(),
                    loginRequestDto.getPassword(),
                    List.of());
            authConfig.getAuthenticationManager().authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            String accessToken = jwtUtils.generateToken(loginRequestDto.getEmail());

            return LoginResponseDto.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .timestamp(LocalDateTime.now(clock))
                    .accessToken(accessToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Provided credentials are not valid");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public RegisterResponseDto createUser(RegisterRequestDto registerRequestDto) {
        String email = registerRequestDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        User toSave = userMapper.mapToUser(registerRequestDto);
        userRepository.save(toSave);

        return RegisterResponseDto.builder()
                .httpStatus(200)
                .message("User has been created")
                .timestamp(LocalDateTime.now(clock))
                .build();
    }

    @Override
    public VerifyTokenResponseDto verifyAuthToken(VerifyTokenRequestDto tokenRequestDto) {
        String jwtSubject;
        try {
            jwtSubject = jwtUtils.verifyToken(tokenRequestDto.getToken());
        } catch (Exception e) {
            throw new TokenVerificationException("Token is not valid");
        }

        if(!userRepository.existsByEmail(jwtSubject)) {
            throw new TokenVerificationException("Token is not valid");
        }
        return VerifyTokenResponseDto.builder()
                .httpStatus(HttpStatus.OK.value())
                .tokenIsValid(true)
                .timestamp(LocalDateTime.now(clock))
                .build();
    }
}
