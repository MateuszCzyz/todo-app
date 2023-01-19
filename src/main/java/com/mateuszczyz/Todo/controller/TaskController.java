package com.mateuszczyz.Todo.controller;

import com.mateuszczyz.Todo.dto.TaskReadDto;
import com.mateuszczyz.Todo.dto.TaskWriteDto;
import com.mateuszczyz.Todo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(originPatterns = "*", exposedHeaders = "**")
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskReadDto>> getAllTasks() {
        List<TaskReadDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskReadDto> getTask(@PathVariable Long id) {
        TaskReadDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<TaskReadDto> saveTask(@Valid @RequestBody TaskWriteDto taskWriteDto) {
        TaskReadDto savedTask = taskService.saveTask(taskWriteDto);
        URI savedTaskURI = URI.create("/api/tasks/" + savedTask.getId());
        return ResponseEntity.created(savedTaskURI).body(savedTask);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean taskDeleted = taskService.deleteTask(id);
        if(taskDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // toggle task status whether is done or not
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<TaskReadDto> toggleTaskStatus(@PathVariable Long id) {
        TaskReadDto updatedTask = taskService.toggleTaskStatus(id);
        return ResponseEntity.ok().body(updatedTask);
    }
}
