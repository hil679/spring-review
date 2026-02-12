package com.example.springintermediate;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("수동 등록 passwordEncoder를 주입 받아 문자열 암호화")
    void Test1() {
        String pw = "1234 abcd";
        String encodedPW = passwordEncoder.encode(pw);
        System.out.println(encodedPW);

        String inputPW = "5678";
        boolean matched = passwordEncoder.matches(inputPW, encodedPW);
        System.out.println(matched);
    }
}
