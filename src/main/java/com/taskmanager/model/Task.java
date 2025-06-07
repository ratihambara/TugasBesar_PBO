package com.taskmanager.model;

import jakarta.persistence.*;
import java.time.LocalDate; // Import LocalDate

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private int priority; // 0: low, 1: medium, 2: high
    private boolean completed; // Added completed field
    private LocalDate dueDate; // Added dueDate field

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //getter dan setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() { // Getter for completed
        return completed;
    }

    public void setCompleted(boolean completed) { // Setter for completed
        this.completed = completed;
    }

    public LocalDate getDueDate() { // Getter for dueDate
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) { // Setter for dueDate
        this.dueDate = dueDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
