
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
        Task task = new Task(idCounter.getAndIncrement(),
                request.title(), request.description(), "PENDING");
        tasks.add(task);
        return task;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable long id) {
        return tasks.stream()
                .filter(t -> t.id() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable long id, @RequestBody TaskRequest request) {
        Task old = getTask(id);
        Task updated = new Task(id, request.title(),
                request.description(), "COMPLETED");
        tasks.set(tasks.indexOf(old), updated);
        return updated;
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable long id) {
        tasks.remove(getTask(id));
        return "Task deleted successfully";
    }

    @GetMapping("/tasks/summary")
    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("totalTasks", (long) tasks.size());
        summary.put("pendingTasks", tasks.stream()
                .filter(t -> t.status().equals("PENDING")).count());
        summary.put("completedTasks", tasks.stream()
                .filter(t -> t.status().equals("COMPLETED")).count());
        return summary;
    }

    @GetMapping("/health")
    public String health() {
        return "Student Task API is running!";
    }
}
