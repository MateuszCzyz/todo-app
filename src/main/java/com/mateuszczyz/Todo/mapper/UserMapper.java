package com.mateuszczyz.Todo.mapper;

import com.mateuszczyz.Todo.dto.RegisterRequestDto;
import com.mateuszczyz.Todo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User mapToUser(RegisterRequestDto registerRequestDto) {
        String encodedPassword = passwordEncoder.encode(registerRequestDto.getPassword());
        return User.builder()
                .email(registerRequestDto.getEmail())
                .password(encodedPassword)
                .build();
    }

}
