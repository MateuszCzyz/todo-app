package com.mateuszczyz.Todo.controller.advice;

import com.mateuszczyz.Todo.controller.TaskController;
import com.mateuszczyz.Todo.exception.ExceptionResponse;
import com.mateuszczyz.Todo.exception.InvalidTaskFormatException;
import com.mateuszczyz.Todo.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice(assignableTypes = TaskController.class)
@RequiredArgsConstructor
public class TaskControllerAdvice {

    private final Clock clock;

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTaskNotFoundException(TaskNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionResponse response = ExceptionResponse.builder()
                .httpStatus(httpStatus.value())
                .messages(List.of(exception.getMessage()))
                .timestamp(LocalDateTime.now(clock))
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTaskFormException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = ExceptionResponse.builder()
                .httpStatus(httpStatus.value())
                .messages(errorMessages)
                .timestamp(LocalDateTime.now(clock))
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }
}
