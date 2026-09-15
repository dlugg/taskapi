package com.example.taskapi;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task){
        tasks.add(task);
    }

    public List<Task> getTasks(){
        return List.copyOf(tasks);
    }

    public List<Task> getTasks(long chatId){
        List<Task> userTasks = new ArrayList<>();
        for (Task task : tasks){
            if (task.getChatId() == chatId){
                userTasks.add(task);
            }
        }
        return userTasks;
    }

}
