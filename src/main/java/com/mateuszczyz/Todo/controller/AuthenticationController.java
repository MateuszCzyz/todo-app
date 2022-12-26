package com.mateuszczyz.Todo.controller;

import com.mateuszczyz.Todo.dto.LoginRequestDto;
import com.mateuszczyz.Todo.dto.LoginResponseDto;
import com.mateuszczyz.Todo.dto.RegisterRequestDto;
import com.mateuszczyz.Todo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
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
    public ResponseEntity<String> createUser(@Valid @RequestBody RegisterRequestDto requestDto) {
        userService.createUser(requestDto);
        return ResponseEntity.ok("User created!");
    }

}
