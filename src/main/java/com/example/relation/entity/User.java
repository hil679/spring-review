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
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    , orphanRemoval = true)
    /* orphanRemoval는 Cascade.REMOVE까지 포함
        orphanRemoval는 ManyToOne에 없음 -> 해당 객체를 참조하고 있는 다른 객체가 있을 수 있다는 것이기에
        확인 안 하고 지우면 참조 부결성 위배
     */
    List<Food> foodList = new ArrayList<>();

    public void addFoodList(Food food) {
        foodList.add(food);
        food.setUser(this); //외래키 설정
    }
}