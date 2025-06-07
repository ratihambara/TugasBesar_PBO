package com.taskmanager.controller;

import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {
    @Autowired private TaskService taskService;
    @Autowired private UserRepository userRepository;

    @GetMapping("/tasks")
    public String viewTasks(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
            if (username == null) {
                return "redirect:/login.html";
            }

        User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) { session.invalidate();
                return "redirect:/login.html";
            }

        return "redirect:/tasks.html";  
    }
}