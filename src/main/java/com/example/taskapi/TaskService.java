package com.example.taskapi;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task){
        taskRepository.save(task);
    }

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getTasks(long chatId){
        List<Task> userTasks = new ArrayList<>();
        for (Task task : tasks){
            if (task.getUserId() == chatId){
                userTasks.add(task);
            }
        }
        return userTasks;
    }
public void deleteTaskByPosition(long chatId, int position){
        List<Task> userTasks = getTasks(chatId);
        if (position<1 || position>userTasks.size()){
            throw new IllegalArgumentException("position can't be less then 1 or more than list of your tasks");
        }else{
            Task userTask = userTasks.get(position-1);
           tasks.remove(userTask);
        }
}

}
