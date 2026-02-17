package com.example.Junit5_practice;

import org.junit.jupiter.api.*;

public class CustomTest {
    @Nested
    @DisplayName("주제 별로 테스트를 그룹지어서 파악하기 좋습니다.") //Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Order
    class Test1 {
        //무조건 순서대로 실행이 아니라서
        @Order(1)
        @Test
        @DisplayName("Test1 클래스")
        void test() {
            System.out.println("\nTest1 클래스");
        }

        @Order(3)
        @Test
        @DisplayName("Test1 - test1()")
        void test1() {
            System.out.println("Test1.test1");
        }

        @Order(2)
        @Test
        @DisplayName("Test1 - test2()")
        void test2() {
            System.out.println("Test1.test2");
        }
    }

    @Nested
    @DisplayName("Test2 다른 주제")
    class Test2 {
        @Test
        @DisplayName("Test2 - test1()")
        void test1() {
            System.out.println("Test2.test1");
        }

        @Test
        @DisplayName("Test2 - test2()")
        void test2() {
            System.out.println("Test2.test2");
        }
    }
}
