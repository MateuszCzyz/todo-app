package com.mateuszczyz.Todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class TaskReadDto {
    private final Long id;
    private final String title;
    private final String description;
    private final Integer importance;
    @JsonFormat(pattern="yyyy-MM-dd")
    private final LocalDate deadline;
    private final boolean done;
}
