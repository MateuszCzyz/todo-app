package com.mateuszczyz.Todo.service;

import com.mateuszczyz.Todo.dto.TaskReadDto;
import com.mateuszczyz.Todo.dto.TaskWriteDto;
import com.mateuszczyz.Todo.exception.TaskNotFoundException;
import com.mateuszczyz.Todo.mapper.TaskMapper;
import com.mateuszczyz.Todo.model.Task;
import com.mateuszczyz.Todo.repository.TaskRepository;
import com.mateuszczyz.Todo.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    public void TaskServiceImpl_GetAllTasks_ReturnsListWithTask() {
        Task sampleTask = Task.builder()
                .title("test")
                .description("test")
                .importance(10)
                .build();
        List<Task> tasks = new ArrayList<>();
        tasks.add(sampleTask);

        TaskRepository taskRepositoryMock = mock(TaskRepository.class);
        when(taskRepositoryMock.findAll()).thenReturn(tasks);

        TaskServiceImpl taskService = new TaskServiceImpl(taskRepositoryMock, taskMapper);
        List<TaskReadDto> taskDtoList = taskService.getAllTasks();

        assertThat(taskDtoList).isNotNull();
        assertThat(taskDtoList.size()).isEqualTo(1);
        assertThat(taskDtoList.get(0).getTitle()).isEqualTo(sampleTask.getTitle());
        assertThat(taskDtoList.get(0).getDescription()).isEqualTo(sampleTask.getDescription());
        assertThat(taskDtoList.get(0).getImportance()).isEqualTo(sampleTask.getImportance());
    }

    @Test
    public void UserServiceImpl_GetTaskById_ReturnsTaskWithId() {
        Task sampleTask = Task.builder()
                .title("test")
                .description("test")
                .importance(10)
                .build();

        TaskRepository taskRepositoryMock = mock(TaskRepository.class);
        when(taskRepositoryMock.findById(anyLong())).thenReturn(Optional.of(sampleTask));

        TaskServiceImpl taskService = new TaskServiceImpl(taskRepositoryMock, taskMapper);
        TaskReadDto taskReadDto = taskService.getTaskById(1L);

        assertThat(taskReadDto).isNotNull();
        assertThat(taskReadDto.getTitle()).isEqualTo(sampleTask.getTitle());
        assertThat(taskReadDto.getDescription()).isEqualTo(sampleTask.getDescription());
        assertThat(taskReadDto.getImportance()).isEqualTo(sampleTask.getImportance());
    }

    @Test
    public void UserServiceImpl_GetTaskById_ThrowsExceptionIfTaskNotExists() {
        TaskRepository taskRepositoryMock = mock(TaskRepository.class);
        when(taskRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        TaskServiceImpl taskService = new TaskServiceImpl(taskRepositoryMock, taskMapper);
        assertThatThrownBy(() -> taskService.getTaskById(1L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task with id 1 not found");
    }

    @Test
    public void UserServiceImpl_SaveTask_SavesTask() {
        TaskWriteDto sampleTask = TaskWriteDto.builder()
                .title("test")
                .description("test")
                .importance(1)
                .build();
        TaskRepository taskRepositoryMock = mock(TaskRepository.class);
        when(taskRepositoryMock.save(any(Task.class))).thenReturn(taskMapper.mapToTask(sampleTask));

        TaskServiceImpl taskService = new TaskServiceImpl(taskRepositoryMock, taskMapper);
        TaskReadDto savedTaskDto = taskService.saveTask(sampleTask);

        ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepositoryMock, times(1)).save(argumentCaptor.capture());

        Task capturedTask = argumentCaptor.getValue();
        assertThat(capturedTask).isNotNull();
        assertThat(capturedTask.getTitle()).isEqualTo(sampleTask.getTitle());
        assertThat(capturedTask.getDescription()).isEqualTo(sampleTask.getDescription());
        assertThat(capturedTask.getImportance()).isEqualTo(sampleTask.getImportance());
        assertThat(capturedTask.isDone()).isFalse();

        assertThat(savedTaskDto.getTitle()).isEqualTo(sampleTask.getTitle());
        assertThat(savedTaskDto.getDescription()).isEqualTo(sampleTask.getDescription());
        assertThat(savedTaskDto.getImportance()).isEqualTo(sampleTask.getImportance());
    }

    @Test
    public void UserServiceImpl_ToggleTaskStatus_ChangesTaskStatus() {
        Task sampleTask = Task.builder()
                .title("test")
                .description("test")
                .importance(10)
                .done(false)
                .build();
        TaskRepository taskRepositoryMock = mock(TaskRepository.class);
        when(taskRepositoryMock.findById(any())).thenReturn(Optional.of(sampleTask));

        TaskServiceImpl userService = new TaskServiceImpl(taskRepositoryMock, taskMapper);
        TaskReadDto updatedTaskDto = userService.toggleTaskStatus(1L);

        assertThat(updatedTaskDto.isDone()).isTrue();
    }

    @Test
    public void UserServiceImpl_ToggleTaskStatus_ThrowsExceptionIfTaskNotExists() {
        TaskWriteDto sampleTask = TaskWriteDto.builder()
                .title("test")
                .description("test")
                .importance(1)
                .build();
        TaskRepository taskRepositoryMock = mock(TaskRepository.class);
        when(taskRepositoryMock.findById(any())).thenReturn(Optional.empty());

        TaskServiceImpl userService = new TaskServiceImpl(taskRepositoryMock, taskMapper);
        assertThatThrownBy(() -> userService.toggleTaskStatus(1L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task with id 1 not found");
    }
}