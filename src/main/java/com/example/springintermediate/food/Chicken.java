package com.example.springintermediate.food;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Chicken implements Food{
    @Override
    public void eat() {
        System.out.println("Eat Chicken");
    }
}
