package com.mateuszczyz.Todo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RegisterResponseDto  {
    private final Integer httpStatus;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;
    private final String message;
}
