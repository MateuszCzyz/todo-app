package com.mateuszczyz.Todo.mapper;

import com.mateuszczyz.Todo.dto.TaskReadDto;
import com.mateuszczyz.Todo.dto.TaskWriteDto;
import com.mateuszczyz.Todo.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task mapToTask(TaskWriteDto taskWriteDto) {
        return Task.builder()
                .title(taskWriteDto.getTitle())
                .description(taskWriteDto.getDescription())
                .importance(taskWriteDto.getImportance())
                .deadline(taskWriteDto.getDeadline())
                .done(false)
                .build();
    }

    public TaskReadDto mapToDto(Task task) {
        return TaskReadDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .importance(task.getImportance())
                .deadline(task.getDeadline())
                .done(task.isDone())
                .build();
    }

}
