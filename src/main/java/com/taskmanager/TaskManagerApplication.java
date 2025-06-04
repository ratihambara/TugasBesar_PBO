package com.taskmanager;

import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, TaskService taskService, UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                // Membuat user admin
                User user = new User();
                user.setUsername("admin");
                user.setPassword("admin123"); // password akan di-encode otomatis di UserService
                userService.save(user);

                // Tambahkan task ke user admin
                taskService.addTask("Belajar Spring Boot", user);
                taskService.addTask("Buat Project Task Manager", user);
            }
        };
    }
}
