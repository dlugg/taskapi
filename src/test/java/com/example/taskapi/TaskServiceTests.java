package com.example.taskapi;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void getTaskWithNonExistingChatIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> taskService.getTasks(999));
    }

    @Test
    void deleteMiddleTaskKeepsOtherTasks() {
        long chatId = 123;
        User user = new User(chatId, "Andrzej");
        userRepository.save(user);

        Task taskOne = new Task();
        taskOne.setTaskText("a");
        taskService.addTask(chatId, taskOne);

        Task taskTwo = new Task();
        taskTwo.setTaskText("b");
        taskService.addTask(chatId, taskTwo);

        Task taskThree = new Task();
        taskThree.setTaskText("c");
        taskService.addTask(chatId, taskThree);

        taskService.deleteTaskByPosition(chatId, 2);
        List<Task> userTasks = taskService.getTasks(chatId);
        assertEquals(2, userTasks.size());
        assertEquals("a", userTasks.getFirst().getTaskText());
        assertEquals("c", userTasks.getLast().getTaskText());


    }
}

