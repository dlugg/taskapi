package com.example.taskapi;

public class Task {
    private final String task;
    private final long chatId;
    public Task(String task, long chatId){
        this.task = task;
        this.chatId = chatId;
    }

    public String getTask() {
        return task;
    }

    public long getChatId() {
        return chatId;
    }
}
