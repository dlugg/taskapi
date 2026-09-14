package com.example.taskapi;

import org.springframework.web.bind.annotation.*;

@RestController
public class PingController {
    @GetMapping("/ping")
    String ping() {
        return "pong";
    }

    @GetMapping("/hello/{chatId}/{task}")
    Task hello(@PathVariable String task, @PathVariable long chatId){
        return new Task(task,chatId);
    }

    @PostMapping("/tasks")
    Task task(@RequestBody Task task){
        return task;
    }
}


