package com.mateuszczyz.Todo.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskReadDto {
    private final Long id;
    private final String title;
    private final String description;
    private final Integer importance;
    private final boolean done;
}
