package com.mateuszczyz.Todo.controller;

import com.mateuszczyz.Todo.dto.request.LoginRequestDto;
import com.mateuszczyz.Todo.dto.request.RegisterRequestDto;
import com.mateuszczyz.Todo.dto.request.VerifyTokenRequestDto;
import com.mateuszczyz.Todo.dto.response.LoginResponseDto;
import com.mateuszczyz.Todo.dto.response.RegisterResponseDto;
import com.mateuszczyz.Todo.dto.response.VerifyTokenResponseDto;
import com.mateuszczyz.Todo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(originPatterns = "*", exposedHeaders = "**")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = userService.authenticateUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> createUser(@Valid @RequestBody RegisterRequestDto requestDto) {
        RegisterResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/verify-auth-token")
    public ResponseEntity<VerifyTokenResponseDto> verifyAuthToken(@RequestBody VerifyTokenRequestDto requestDto) {
        VerifyTokenResponseDto verifyTokenResponseDto = userService.verifyAuthToken(requestDto);
        return ResponseEntity.ok(verifyTokenResponseDto);
    }
}
