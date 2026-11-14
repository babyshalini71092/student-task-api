package com.example.student_task_api;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong(1);

    record Task(long id, String title, String description, String status) {}

    record TaskRequest(String title, String description) {}

    @PostMapping("/tasks")
    public Task addTask(@RequestBody TaskRequest request) {
        Task task = new Task(
            idCounter.getAndIncrement(),
            request.title(),
            request.description(),
            "PENDING"
        );

        tasks.add(task);
        return task;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    @GetMapping("/health")
    public String health() {
        return "Student Task API is running!";
    }
}