package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasksByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return taskRepository.findByUser(user);
    }

    public Task createTask(String username, Task task) {
        User user = userRepository.findByUsername(username);
        task.setUser(user);
        return taskRepository.save(task);
    }
}