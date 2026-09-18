package com.example.taskapi;

import org.springframework.web.bind.annotation.*;

@RestController
public class PingController {
    @GetMapping("/ping")
    String ping() {
        return "pong";
    }
}



