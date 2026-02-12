package com.example.springintermediate.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class AuthController {
    public final static String AUTHORIZATION_HEADER = "Authorizaion";

    @GetMapping("/create-cookie")
    public String createCookie(HttpServletResponse res) {
        addCookie("new Cookie", res);
        return "creatCookie";
    }

    @GetMapping("/get-cookie")
    public String getCookie(@CookieValue(AUTHORIZATION_HEADER) String value) {
        System.out.println(value);
        return "getCookie: " + value;
    }

    public static void addCookie(String value, HttpServletResponse res) {
        try {
            value = URLEncoder.encode(value, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, value);
            cookie.setPath("/");
            res.addCookie(cookie);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/create-session")
    public String createSession(HttpServletRequest req) {
        // 세션이 존재할 경우 세션 반환, 없을 경우 새로운 세션을 생성한 후 반환
        HttpSession session = req.getSession(true);

        // 세션에 저장될 정보 Name - Value 를 추가합니다
        session.setAttribute(AUTHORIZATION_HEADER, "new Session");
        return "createSession";
    }

    @GetMapping("/get-session")
    public String getSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session.getAttribute(AUTHORIZATION_HEADER).toString();
    }
}
