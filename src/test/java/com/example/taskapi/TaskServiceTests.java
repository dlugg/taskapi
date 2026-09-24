package com.example.taskapi;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class TaskServiceTests {
    private final static long CHAT_ID = 123;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskService taskService;

    @BeforeEach
    void createUser() {
        User user = new User(CHAT_ID, "Andrzej");
        userRepository.save(user);
    }

    @Test
    void addTaskSavesTask() {
        Task task = new Task();
        task.setTaskText("buy bread");
        taskService.addTask(CHAT_ID, task);
        List<Task> userTask = taskService.getTasks(CHAT_ID);
        assertEquals("buy bread", userTask.getLast().getTaskText());
    }

    @Test
    void getTaskWithNonExistingChatIdThrowsException() {
        assertThrows(UserNotFoundException.class, () -> taskService.getTasks(999));
    }

    @Test
    void deleteMiddleTaskKeepsOtherTasks() {
        Task taskOne = new Task();
        taskOne.setTaskText("a");
        taskService.addTask(CHAT_ID, taskOne);

        Task taskTwo = new Task();
        taskTwo.setTaskText("b");
        taskService.addTask(CHAT_ID, taskTwo);

        Task taskThree = new Task();
        taskThree.setTaskText("c");
        taskService.addTask(CHAT_ID, taskThree);

        taskService.deleteTaskByPosition(CHAT_ID, 2);
        List<Task> userTasks = taskService.getTasks(CHAT_ID);
        assertEquals(2, userTasks.size());
        assertEquals("a", userTasks.getFirst().getTaskText());
        assertEquals("c", userTasks.getLast().getTaskText());
    }

    @Test
    void deleteTaskAtInvalidPositionThrows() {
        Task taskOne = new Task();
        taskOne.setTaskText("a");
        taskService.addTask(CHAT_ID, taskOne);

        Task taskTwo = new Task();
        taskTwo.setTaskText("b");
        taskService.addTask(CHAT_ID, taskTwo);

        Task taskThree = new Task();
        taskThree.setTaskText("c");
        taskService.addTask(CHAT_ID, taskThree);
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTaskByPosition(CHAT_ID, 0));
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTaskByPosition(CHAT_ID, 4));
    }
}

