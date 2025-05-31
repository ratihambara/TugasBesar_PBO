package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{username}")
    public List<Task> getTasks(@PathVariable String username) {
        return taskService.getTasksByUsername(username);
    }

    @PostMapping("/{username}")
    public Task createTask(@PathVariable String username, @RequestBody Task task) {
        return taskService.createTask(username, task);
    }
}