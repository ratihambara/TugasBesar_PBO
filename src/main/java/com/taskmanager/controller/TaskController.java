package com.taskmanager.controller;

import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class TaskController {
    @Autowired private TaskService taskService;
    @Autowired private UserRepository userRepository;

    @GetMapping("/")
    public String viewTasks(Model model, Principal principal) {
        System.out.println("✅ Masuk ke GET /");
        if (principal != null) {
            System.out.println("🔐 Username: " + principal.getName());
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            model.addAttribute("tasks", taskService.getTasks(user));
            return "tasks"; 
        } else {
            System.out.println("❌ Principal null");
            return "redirect:/login";
        }
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String description, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        taskService.addTask(description, user);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }
}
