package com.example.taskapi;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long chatId;
    private String name;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public long getChatId() {
        return chatId;
    }

    public String getName() {
        return name;
    }
}
