package com.example.relation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    //order통한 관계로 조회할 생각이 없다면 아래처럼 관계 안 맺어도 됨.
    @OneToMany(mappedBy = "food")
    private List<Order> orderList = new ArrayList<>();
}