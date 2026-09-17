package com.example.taskapi;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskText;
    private long userId;
    private boolean isDone;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public Task() {

    }

    public Long getId() {
        return id;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getTaskText() {
        return taskText;
    }

    public long getUserId() {
        return userId;
    }
}
