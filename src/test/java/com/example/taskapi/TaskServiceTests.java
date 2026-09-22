package com.example.taskapi;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class TaskServiceTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskService taskService;

    @Test
    void addTaskSavesTask() {
        long chatId = 123;
        User user = new User(chatId, "Andrzej");
        userRepository.save(user);
        Task task = new Task();
        task.setTaskText("buy bread");
        taskService.addTask(chatId, task);
        List<Task> userTask = taskService.getTasks(chatId);
        assertEquals("buy bread", userTask.getLast().getTaskText());
    }
}

