package com.example.resttemlateclient.entity;

import lombok.Getter;

//사실 DB 연결 entity 클래스 아니고 일반 클래스
@Getter
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
