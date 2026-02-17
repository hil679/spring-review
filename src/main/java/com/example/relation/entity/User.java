package com.example.relation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // User Entity를 저장할 때 연관된 Food Entity까지 자동으로 저장
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Food> foodList = new ArrayList<>();

    public void addFoodList(Food food) {
        foodList.add(food);
        food.setUser(this); //외래키 설정
    }
}