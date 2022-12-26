package com.mateuszczyz.Todo.service.impl;

import com.mateuszczyz.Todo.dto.TaskReadDto;
import com.mateuszczyz.Todo.dto.TaskWriteDto;
import com.mateuszczyz.Todo.exception.TaskNotFoundException;
import com.mateuszczyz.Todo.mapper.TaskMapper;
import com.mateuszczyz.Todo.model.Task;
import com.mateuszczyz.Todo.repository.TaskRepository;
import com.mateuszczyz.Todo.service.TaskService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskReadDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskReadDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException(id));
        return taskMapper.mapToDto(task);
    }

    @Override
    public TaskReadDto saveTask(TaskWriteDto taskWriteDto) {
        Task target = taskMapper.mapToTask(taskWriteDto);
        Task savedTask = taskRepository.save(target);
        return taskMapper.mapToDto(savedTask);
    }

    @Override
    @Transactional
    public TaskReadDto toggleTaskStatus(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setDone(!task.isDone());
        return taskMapper.mapToDto(task);
    }
}
