package com.example.springintermediate.food;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("pizza")
public class Pizza implements Food{
    @Override
    public void eat() {
        System.out.println("Eat Pizza");
    }
}
