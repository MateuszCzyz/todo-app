package com.mateuszczyz.Todo.service;

import com.mateuszczyz.Todo.dto.LoginRequestDto;
import com.mateuszczyz.Todo.dto.LoginResponseDto;
import com.mateuszczyz.Todo.dto.RegisterRequestDto;
import com.mateuszczyz.Todo.dto.RegisterResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto);

    RegisterResponseDto createUser(RegisterRequestDto registerRequestDto);
}
