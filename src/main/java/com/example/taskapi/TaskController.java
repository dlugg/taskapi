package com.example.taskapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    void addTask(@RequestBody Task task) {
        taskService.addTask(task);
    }

    @GetMapping("/tasks")
    List<Task> task() {
        return taskService.getTasks();
    }

    @GetMapping("/tasks/{chatId}")
    List<Task> getUserTasks(@PathVariable long chatId) {
        return taskService.getTasks(chatId);
    }
}
