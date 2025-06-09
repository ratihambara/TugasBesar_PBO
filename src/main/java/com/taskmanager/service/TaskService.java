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

        public Task save(Task task) { // Modified to accept Task object and return it
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task taskDetails, User user) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null && task.getUser().getId().equals(user.getId())) {
            task.setDescription(taskDetails.getDescription());
            task.setPriority(taskDetails.getPriority());
            task.setCompleted(taskDetails.isCompleted()); // Allow updating completed status via edit too

            // Handle dueDate update to prevent accidental clearing
            if (taskDetails.getDueDate() != null) {
                task.setDueDate(taskDetails.getDueDate());
            } else {
                // If the due date from taskDetails is null,
                // only set the task's due date to null if it was already null.
                // This preserves an existing due date if the form field was unintentionally empty.
                if (task.getDueDate() == null) {
                    task.setDueDate(null);
                }
                // If task.getDueDate() was not null, and taskDetails.getDueDate() is null,
                // we do nothing, preserving the existing task.getDueDate().
            }
            return taskRepository.save(task);
        }
        return null; // Or throw exception
    }

    public Task toggleComplete(Long taskId, User user) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null && task.getUser().getId().equals(user.getId())) { // Check if task exists and belongs to the user
            task.setCompleted(!task.isCompleted());
            return taskRepository.save(task);
        }
        return null; // Or throw an exception if task not found or not owned by user
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

    public void deleteAllTasks(User user) {
        List<Task> tasks = taskRepository.findByUser(user);
        taskRepository.deleteAll(tasks);
    }
}
