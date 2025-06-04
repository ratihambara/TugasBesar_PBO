package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired private TaskRepository taskRepository;

    public List<Task> getTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public void addTask(String description, User user) {
        Task task = new Task();
        task.setDescription(description);
        task.setUser(user);
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
