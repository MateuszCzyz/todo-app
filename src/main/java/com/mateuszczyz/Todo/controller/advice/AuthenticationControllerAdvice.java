package com.mateuszczyz.Todo.controller.advice;

import com.mateuszczyz.Todo.controller.AuthenticationController;
import com.mateuszczyz.Todo.dto.response.VerifyTokenResponseDto;
import com.mateuszczyz.Todo.exception.ExceptionResponse;
import com.mateuszczyz.Todo.exception.TokenVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;


@RestControllerAdvice(assignableTypes = AuthenticationController.class)
@RequiredArgsConstructor
public class AuthenticationControllerAdvice {
    private final Clock clock;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionResponse responseBody = ExceptionResponse.builder()
                .httpStatus(httpStatus.value())
                .message("Provided credentials are not valid")
                .timestamp(LocalDateTime.now(clock))
                .build();
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionResponse responseBody = ExceptionResponse.builder()
                .httpStatus(httpStatus.value())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now(clock))
                .build();
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    @ExceptionHandler ResponseEntity<VerifyTokenResponseDto> handleTokenVerificationException(
            TokenVerificationException exception) {
        VerifyTokenResponseDto responseDto = VerifyTokenResponseDto.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .tokenIsValid(false)
                .timestamp(LocalDateTime.now(clock))
                .build();
        return ResponseEntity.badRequest().body(responseDto);
    }
}
