package com.example.springprepare.request;

import lombok.Getter;
import lombok.Setter;

@Setter // @ModelAttribute가 객체 채울 때 setter를 통해 값을 넣는 방식이여서 필요
@Getter
public class Star {
    String name;
    int age;

    public Star(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Star() {}
}