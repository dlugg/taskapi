package com.example.taskapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
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

    @DeleteMapping("/tasks/{chatId}/{position}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTask(@PathVariable long chatId, @PathVariable int position) {
        taskService.deleteTaskByPosition(chatId, position);
    }

}
