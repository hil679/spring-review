package com.example.springintermediate.food;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // for DI
public class FoodTest {
    /*
    [ERROR]
    available: expected single matching bean but found 2: chicken,pizza
    @Autowired
    Food food;

    @Test
    void Test1() {
        food.eat();
    }
    => 기본적으로 AutoWired는 type으로 bean을 찾고 -> 같으면 이름으로 찾음
        따라서 아래 코드 정상
     */

    @Autowired
    Food pizza;
    @Autowired
    Food chicken;

    @Test
    void test2() {
        pizza.eat();// Chicken에 Primary붙이는 순간 chicken 객체것으로 바뀜
        chicken.eat();
    }

    @Autowired
    Food food; //Chicken에 @Primary 붙임
    @Test
    void test3() {
        food.eat();
    }

    @Autowired
    @Qualifier("pizza")
    Food food2;
    @Test
    void test4() {
        food2.eat();
    }






}
