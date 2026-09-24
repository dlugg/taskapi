package com.example.taskapi;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

     private long findUserIdByChatId(long chatId) {
        Optional<User> user = userRepository.findByChatId(chatId);
        if (user.isPresent()) {
            return user.get().getId();
        } else {
            throw new UserNotFoundException("no user with this chatId found " + chatId);
        }
    }

    public void addTask(long chatId, Task task) {
        long userId = findUserIdByChatId(chatId);
        task.setUserId(userId);
        taskRepository.save(task);
    }

    public List<Task> getTasks(long chatId) {
        long userId = findUserIdByChatId(chatId);
        return taskRepository.findByUserIdOrderByIdAsc(userId);
    }

    public void deleteTaskByPosition(long chatId, int position) {
        List<Task> userTasks = getTasks(chatId);
        if (position < 1 || position > userTasks.size()) {
            throw new IllegalArgumentException("position can't be less then 1 or more than list of your tasks");
        } else {
            Task userTask = userTasks.get(position - 1);
            taskRepository.delete(userTask);

        }

    }

}
