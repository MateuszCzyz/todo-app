package com.mateuszczyz.Todo.service;

import com.mateuszczyz.Todo.dto.request.LoginRequestDto;
import com.mateuszczyz.Todo.dto.request.RegisterRequestDto;
import com.mateuszczyz.Todo.dto.request.VerifyTokenRequestDto;
import com.mateuszczyz.Todo.dto.response.LoginResponseDto;
import com.mateuszczyz.Todo.dto.response.RegisterResponseDto;
import com.mateuszczyz.Todo.dto.response.VerifyTokenResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto);

    RegisterResponseDto createUser(RegisterRequestDto registerRequestDto);

    VerifyTokenResponseDto verifyAuthToken(VerifyTokenRequestDto tokenRequestDto);
}
