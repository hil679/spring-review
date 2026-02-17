package com.example.relation;

import com.example.relation.entity.Food;
import com.example.relation.entity.User;
import com.example.relation.repository.FoodRepository;
import com.example.relation.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
public class ManyToManyTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("N대M 단방향 테스트")
    void test1() {

        User user = new User();
        user.setName("Robbie");

        User user2 = new User();
        user2.setName("Robbert");

        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);
        food.getUserList().add(user);
        food.getUserList().add(user2);

        userRepository.save(user);
        userRepository.save(user2);
        foodRepository.save(food);

        // 자동으로 중간 테이블 orders 가 create 되고 insert 됨을 확인할 수 있습니다.
    }
}
