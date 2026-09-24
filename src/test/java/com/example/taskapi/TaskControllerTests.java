package com.example.taskapi;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerTests {
    private static final long CHAT_ID = 123;
    @Autowired
    MockMvc mockMvc;
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
    void postTaskReturnsCreated() throws Exception {
        mockMvc.perform(post("/tasks/{chatId}", CHAT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskText\":\"хлеб\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void postTaskForUnknownChatIdReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskText\":\"buy bread\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTasksReturnTasksInOrder() throws Exception {
        Task taskOne = new Task();
        taskOne.setTaskText("a");
        taskService.addTask(CHAT_ID, taskOne);

        Task taskTwo = new Task();
        taskTwo.setTaskText("b");
        taskService.addTask(CHAT_ID, taskTwo);

        mockMvc.perform(get("/tasks/{chatId}", CHAT_ID))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].taskText").value("a"))
                .andExpect(jsonPath("$[1].taskText").value("b"));
    }
}
