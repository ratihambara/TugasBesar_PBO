package com.taskmanager;

import com.taskmanager.model.Task; 
import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

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
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword("admin123"); // password will be encoded by UserService
                userService.save(adminUser);

                                // Task 1 for admin
                Task task1 = new Task();
                task1.setUser(adminUser);
                task1.setDescription("Belajar Spring Boot");
                task1.setPriority(1); // Medium priority
                task1.setDueDate(LocalDate.now().plusDays(7)); // Due in 7 days
                task1.setCompleted(false);
                taskService.save(task1);

                // Task 2 for admin
                Task task2 = new Task();
                task2.setUser(adminUser);
                task2.setDescription("Buat Project Task Manager");
                task2.setPriority(2); // High priority
                task2.setDueDate(LocalDate.now().plusDays(3)); // Due in 3 days
                task2.setCompleted(false);
                taskService.save(task2);

                // Task 3 for admin
                Task task3 = new Task();
                task3.setUser(adminUser);
                task3.setDescription("Review Pull Request #42");
                task3.setPriority(0); // Low priority
                task3.setDueDate(LocalDate.now().plusDays(1)); // Due tomorrow
                task3.setCompleted(true); // Already completed
                taskService.save(task3);

                // Task 4 for admin - No due date
                Task task4 = new Task();
                task4.setUser(adminUser);
                task4.setDescription("Refactor Authentication Module");
                task4.setPriority(1); // Medium priority
                task4.setDueDate(LocalDate.now().plusDays(1)); // No specific due date
                task4.setCompleted(false);
                taskService.save(task4);
            }
        };
    }
}
