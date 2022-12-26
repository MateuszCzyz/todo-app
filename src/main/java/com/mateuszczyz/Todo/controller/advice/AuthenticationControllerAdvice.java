package com.mateuszczyz.Todo.controller.advice;

import com.mateuszczyz.Todo.controller.AuthenticationController;
import com.mateuszczyz.Todo.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice(assignableTypes = AuthenticationController.class)
@RequiredArgsConstructor
public class AuthenticationControllerAdvice {
    private final Clock clock;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .httpStatus(httpStatus.value())
                .messages(errorMessages)
                .timestamp(LocalDateTime.now(clock))
                .build();
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionResponse responseBody = ExceptionResponse.builder()
                .httpStatus(httpStatus.value())
                .messages(List.of(exception.getMessage()))
                .timestamp(LocalDateTime.now(clock))
                .build();
        return new ResponseEntity<>(responseBody, httpStatus);
    }
}
