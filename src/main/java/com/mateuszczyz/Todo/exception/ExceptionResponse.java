package com.mateuszczyz.Todo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionResponse {
    private final Integer httpStatus;
    private final String message;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;
}
