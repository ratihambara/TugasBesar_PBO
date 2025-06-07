package com.taskmanager.controller;

import com.taskmanager.model.User;
import com.taskmanager.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    @Autowired private UserService userService;

    @GetMapping("/register")
public String registerForm() {
    return "redirect:/register.html";
}

@PostMapping("/register")
public String registerSubmit(@ModelAttribute User user) {
    userService.save(user);
    return "redirect:/login.html";
}

@GetMapping("/login")
public String loginForm() {
    return "redirect:/login.html";
}

@GetMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login.html";
}
}
