package com.mateuszczyz.Todo.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskReadDto {
    private Long id;
    private String title;
    private String description;
    private Integer importance;
    private boolean done;
}
