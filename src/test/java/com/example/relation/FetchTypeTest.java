package com.example.relation;

import com.example.relation.entity.Food;
import com.example.relation.entity.User;
import com.example.relation.repository.FoodRepository;
import com.example.relation.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FetchTypeTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void init() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("Robbie");
        userList.add(user1);

        User user2 = new User();
        user2.setName("Robbert");
        userList.add(user2);
        userRepository.saveAll(userList);

        List<Food> foodList = new ArrayList<>();
        Food food1 = new Food();
        food1.setName("고구마 피자");
        food1.setPrice(30000);
        food1.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food1);

        Food food2 = new Food();
        food2.setName("아보카도 피자");
        food2.setPrice(50000);
        food2.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food2);

        Food food3 = new Food();
        food3.setName("후라이드 치킨");
        food3.setPrice(15000);
        food3.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food3);

        Food food4 = new Food();
        food4.setName("후라이드 치킨");
        food4.setPrice(15000);
        food4.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food4);

        Food food5 = new Food();
        food5.setName("고구마 피자");
        food5.setPrice(30000);
        food5.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food5);
        foodRepository.saveAll(foodList);
    }

    @Test
    @DisplayName("아보카도 피자 조회")
    /*
            select
            f1_0.id,
            f1_0.name,
            f1_0.price,
            u1_0.id,
            u1_0.name
        from
            food f1_0
        left join
            users u1_0
                on u1_0.id=f1_0.user_id
        where
            f1_0.id=?
     */
    @Transactional //지연로딩쓰려면 걸어줘
    /*
    지연로딩 시 쿼리
     select
        f1_0.id,
        f1_0.name,
        f1_0.price,
        f1_0.user_id
    from
        food f1_0
    where
        f1_0.id=?
food.getName() = 아보카도 피자
food.getPrice() = 50000.0
아보카도 피자를 주문한 회원 정보 조회
Hibernate:
    select
        u1_0.id,
        u1_0.name
    from
        users u1_0
    where
        u1_0.id=?
     */
    void test1() {
        Food food = foodRepository.findById(2L).orElseThrow(NullPointerException::new);

        System.out.println("food.getName() = " + food.getName());
        System.out.println("food.getPrice() = " + food.getPrice());
        /*
        아보카도 피자 정보만 알고 싶어도 join으로 user정보까지 같이 가져옴.
        따라서 JPA에서는 정보를 바로 가져올지, 필요할 때 가져올지 정할 수가 있음.
        가져오는 방법을 FetchType이라 하며, 2가지가 있음 (LAZY, EAGER)
         */
        System.out.println("아보카도 피자를 주문한 회원 정보 조회");
        System.out.println("food.getUser().getName() = " + food.getUser().getName());
    }

    @Test
    @Transactional
    @DisplayName("Robbie 고객 조회")
    void test2() {
        //이때 FoodList까지 가져오지 않음
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        //이때 user의 pk가지고(위에서 이미 find했으니까 가지고 있음) food 정보 조회
        for (Food food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }

    @Test
    @DisplayName("Robbie 고객 조회 실패")
    void test3() {
        //OneToMany니까 이미 default가 LAZY -> 따라서 @Transactional 필요
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        for (Food food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }

}
