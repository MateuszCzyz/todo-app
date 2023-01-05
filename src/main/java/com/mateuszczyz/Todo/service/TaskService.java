package com.mateuszczyz.Todo.service;

import com.mateuszczyz.Todo.dto.TaskReadDto;
import com.mateuszczyz.Todo.dto.TaskWriteDto;

import java.util.List;

public interface TaskService {

    List<TaskReadDto> getAllTasks();

    TaskReadDto getTaskById(Long id);

    TaskReadDto saveTask(TaskWriteDto taskWriteDto);

    boolean deleteTask(Long id);

    TaskReadDto toggleTaskStatus(Long id);
}
