package com.mateuszczyz.Todo.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ExceptionResponse {
    private Integer httpStatus;
    private List<String> messages;
    private LocalDateTime timestamp;
}
