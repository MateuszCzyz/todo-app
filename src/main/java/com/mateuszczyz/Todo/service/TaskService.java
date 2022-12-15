package com.mateuszczyz.Todo.service;

import com.mateuszczyz.Todo.dto.TaskReadDto;
import com.mateuszczyz.Todo.dto.TaskWriteDto;

import java.util.List;

public interface TaskService {

    List<TaskReadDto> getAllTasks();

    TaskReadDto getTaskById(Long id);

    TaskReadDto saveTask(TaskWriteDto taskWriteDto);

    TaskReadDto toggleTaskStatus(Long id);
}
