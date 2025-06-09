package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class TaskController {
    @Autowired private TaskService taskService;

    @GetMapping("/tasks")
    @ResponseBody
    public List<Task> getTasksJson(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        return taskService.getTasks(user);
    }

    @PostMapping("/tasks/add")
    @ResponseBody
    public Task addTask(@RequestBody Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        // Ensure new tasks are not marked completed by default unless specified
        if (task.getId() == null) { // Only for new tasks
            task.setCompleted(false); 
        }
        task.setUser(user); // Set the user for the new task
        return taskService.save(task); // Save and return the persisted task (with ID)
    }

    @PutMapping("/tasks/{id}") // Added PUT mapping for editing a task
    @ResponseBody
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        return taskService.updateTask(id, taskDetails, user);
    }

    @PutMapping("/tasks/{id}/complete") // Added PUT mapping for toggling complete status
    @ResponseBody
    public Task toggleCompleteTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        return taskService.toggleComplete(id, user);
    }

    @DeleteMapping("/tasks/{id}") // Using DELETE method and path variable for task ID
    @ResponseBody
    public void deleteTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        // Optional: Check if the task belongs to the user before deleting
        taskService.deleteTask(id); // Assuming deleteTask checks ownership or is admin-only
    }

    @DeleteMapping("/tasks/all") // Added DELETE mapping for clearing all tasks
    @ResponseBody
    public void deleteAllTasks(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        taskService.deleteAllTasks(user);  
    }
}